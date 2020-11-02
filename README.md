# IA Project: Azamon

[![HitCount](http://hits.dwyl.io/ArnauSoler/IA.svg)](http://hits.dwyl.io/ArnauSoler/IA)  [![GitHub stars](https://img.shields.io/github/stars/ArnauSoler/IA.svg)](https://GitHub.com/ArnauSoler/IA/stargazers/)  [![GitHub forks](https://img.shields.io/github/forks/ArnauSoler/IA.svg)](https://GitHub.com/ArnauSoler/IA/network/)  [![GitHub repo size in bytes](https://img.shields.io/github/repo-size/ArnauSoler/IA.svg)](https://github.com/ArnauSoler/IA)  [![GitHub contributors](https://img.shields.io/github/contributors/ArnauSoler/IA.svg)](https://GitHub.com/ArnauSoler/IA/graphs/contributors/)  

## Package distribution optimization using local search algorithms

**Authors**: Dànae Canillas, Miquel Escobar, Arnau Soler

*2020-2021 Q1 - Universitat Politècnica de Catalunya, UPC*

## Table of Contents

1. [Description](#Description)
2. [Main Goal](#Main-Goal)
2. [Usage](#Usage)
   1. [Project files](#Project-files)
   2. [Execution steps](#Execution-steps)
3. [License](#License)

## Problem Description

We are facing a problem raised by the fictitious company Azamon.  Azamon, like Amazon, is a product distribution company that has to deal with daily package distributions working side byside with transport companies, the ones who are responsible for making shipments to customers.This  company  needs  a  logistics  optimization  system  for  its  shipments  in  order  to  be  efficient,minimizing its storage needs and shipping costs.

What is the secret of the success of these companies?  The satisfaction of the customers sincethey have the security that all their orders will be received in the estimated time and sometimesearlier than expected, which increases their happiness.

## Main Goal

The main objective of the problem is to obtain for all the packages to be sent in one day in aconcrete city,  an optimized day shipping offers distribution from the transport companies.  Inthe design of the solution we should take into account the costs associated with transport andstorage and the customers happiness.

We  can  solve  this  problem  using  local  search  since  we  are  not  interested  in  the  way  to  reachthe  solution.   We  are  only  interested  in  arriving  at  the  best  possible  solution  achievable  in  areasonable time, without the need to reach the optimum since the solution space is very large.

To solve it, we will start from an initial solution that we will try to improve by using differentoperators that will discover neighboring solutions.

## Usage

### Project files

### Execution steps

#### - CREATE JAR FILE:

	·····················································
	1. Generate the class files:
	·····················································
	$ javac -cp "lib/*" IA/Azamon/*.java

	·····················································
	2. Compile to obtain the executable
	·····················································
	$ jar cvfm Azamon.jar Manifest.txt IA/Azamon/*.class


#### - EXECUTE THE JAR FILE:

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
