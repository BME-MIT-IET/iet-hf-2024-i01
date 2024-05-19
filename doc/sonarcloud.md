**SonarCloud documentation**

*What is SonarCloud?*

SonarCloud is a cloud-based code analysis service designed to detect coding issues. The code is checked against an extensive set of rules that cover many attributes of code, such as maintainability, reliability, and security issues on each merge/pull request. SonarCloud completes the analysis loop to deliver clean code that meets high-quality standards.

*Why did we decide to add it?*

Since we knew that it would be beneficial to check the existing code's quality, and also to analyze future code additions.

*How do you see the details / measures?*

It also shows its result on the GitHub website, but you can also find the detailed facts on the SonarCloud website under https://sonarcloud.io/project/overview?id=BME-MIT-IET_iet-hf-2024-i01

*What was the aftermath of adding SonarCloud?*

SC detected more than 40 high severity issues. It consisted of the following problems:

- The built in Random class' objects should be initialized only once per class

- File input/ouput stream should be used inside a try-with-resources blocks

- Too complex functions' logic should be split among several sub-functions to increase readability 

- Empty functions should be explained with comments

- Perplexing field / method names should be changed

So all of these issues have been fixed. It was pretty time-consuming to fix all of these, especially the function refactoring (to reduce method complexity). Also I have marked the existing problems as intentional on the SonarCloud webiste (e.g. a Logger class would be unnecessary to add in our case).

**Results:**

This tool proved to be useful not only by pointing out code-smells, but also identified security hotspots, gave us test code-coverage detailed data and its quality gates helped us with reviewing new code.
All in all we started out with 370 active issues, and ended with just 187 issues (only 1/4 of this is medium, the other 3/4 are classified with small severity). 

//to do add image!!!