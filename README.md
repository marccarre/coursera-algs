coursera-algs4partI - Coursera, Princeton, Algorithms, Part I
========

- Features:
	- Gradle can be used to build the project and/or configure your favourite IDE.
	- Continuous compilation and testing is achieved using Python (`build.py`) on top of Gradle (`build.gradle`) and is triggered every time a change in the sources is saved.


- Run:
	- `gradle idea` or `gradle eclipse` to configure your favourite IDE.
	- `gradle check` to compile and test.
	- `gradle zipPart2Week1` to automatically prepare the archive to upload to Coursera.
	- `python build.py` to compile and test continuously using Gradle.
	- `python build.py <gradle tasks>` to continuously run the specified Gradle tasks.
