#!/usr/local/bin/python
#
# Description: 
#   Watch for file changes under ./src, and 
#   trigger the configured gradle command on change.
#   Default command is 'gradle check'.
#
# Example:
#   ./build.py                         # Runs 'gradle check'
#   ./build.py clean compileJava test  # Runs 'gradle clean compileJava test'
#
# Dependencies:
#   watchdog
#
import sys
from os.path import dirname, realpath, join
import time
import subprocess
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler, FileModifiedEvent


CURRENT_PATH = realpath(dirname(__file__))


class FileChangeHandler(FileSystemEventHandler):
    def __init__(self, cmd):
        self.cmd = cmd

    def on_modified(self, event):
        if (type(event) == FileModifiedEvent):
            print('%s: change in file %s detected.' % (time.asctime(), self.get_path(event)))
            subprocess.call(self.cmd)

    def get_path(self, event):
        return event.src_path.replace(CURRENT_PATH, '')


def wait_for_keyboard_interrupt(observer):
    try:
        while True:
            time.sleep(0.1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()


if __name__ == '__main__':
    cmd = ['gradle'] + sys.argv[1:] if len(sys.argv) > 1 else ['gradle', 'check']
    path = 'src'

    observer = Observer()
    observer.schedule(FileChangeHandler(cmd), path=path, recursive=True)

    print('Now watching changes in files under: \n\t%s\nThe following command will be run on any change: \n\t%s\n' %
        (join(CURRENT_PATH, path), ' '.join(cmd)))

    observer.start()
    wait_for_keyboard_interrupt(observer)
