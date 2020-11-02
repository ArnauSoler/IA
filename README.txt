CREATE THE JAR FILE:

	·····················································
	1. Generate the class files:
	·····················································
	$ javac -cp "lib/*" IA/Azamon/*.java

	·····················································
	2. Compile to obtain the executable
	·····················································
	$ jar cvfm Azamon.jar Manifest.txt IA/Azamon/*.class


EXECUTE THE JAR FILE:

	$ java -jar Azamon.jar

	Home page:

	###########################################
		                                   
	  /_\   ____ __ _  _ __ ___    ___   _ __  
	 //_\\ |_  // _` || '_ ` _ \  / _ \ | '_ \ 
	/  _  \ / /| (_| || | | | | || (_) || | | |
	\_/ \_//___|\__,_||_| |_| |_| \___/ |_| |_|
		                                   
	###########################################
	### 1.	Create an Experiment		###
	### 2.	Run an Experiment		###
	### 3.	Exit				###
	###########################################
	
	1. You can create an experiment, assigning the required values

		* Be aware! The decimal separator is the comma!

		- Select an Algorithm
			- Hill Climbing
			- Simulated Annealing
		- Enter the number of packages
		- Enter a package generator seed: (integer)
		- Enter a proportion for transports: (double)
		- Enter a transport generator seed: (integer)
		- Select the Generator
			- Generator A (Optimized Price)
			- Generator B (Optimized Happiness)
			- Generator C (Bad Initialization)
		- Select the Heuristic
			- Minimize the transport/storage cost
			- Minimize the transport/storage cost & Maximize customer happiness
		- Select the Operators
			- Move
			- Swap
			- Move & Swap

	2. Run an experiment from the project statement.
 
		* The results will be stored inside the outputs folder.

		- Select an Experiment:
			- Special Experiment (output on the terminal)
			- Experiment Number 1 
			- Experiment Number 2 
			- Experiment Number 3  			
			- Experiment Number 4
			- Experiment Number 6 
			- Experiment Number 7.1 
			- Experiment Number 7.2
			- Experiment Number 7.4
			- Experiment Number 7.6 

	

	

