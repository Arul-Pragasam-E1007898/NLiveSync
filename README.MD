## Nursery Live Sync
This is a Java project that provides a solution for doing bulk upsert of contacts using publicly exposed API.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- Java 11 or later
- [Maven](https://maven.apache.org/) (for building and managing dependencies)

### Installing

1. Clone the repository to your local machine

git clone https://github.com/Arul-Pragasam-E1007898/NLiveSync.git

2. Navigate to the project directory

cd NLiveSync

3. Build the project using Maven

mvn clean install

### Running

  `java -jar  
    -DFILE="/Users/apragasam/Documents/Work/Tmp/create_nldata_5L.csv" 
    -DFSBASE="demo.myfreshworks.com" 
    -DTOKEN=123 NLiveSync.jar`


## Built With

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - The programming language used
- [Maven](https://maven.apache.org/) - Dependency Management

## Authors


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
