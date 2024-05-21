#!/bin/bash

# Define the first ssh state file
FIRST_SSH_STATE_FILE="/home/vagrant/.vagrant-first-ssh"

# Set the display if it's not set and the SSH connection is available
if [[ -z "$DISPLAY" && "${SSH_CONNECTION}" ]]; then
  export DISPLAY=$(echo $SSH_CONNECTION | cut -d " " -f 1):0.0
fi 
# Only start the Java application if it hasn't been started before, and copy the images to the correct location
if [ ! -f ${FIRST_SSH_STATE_FILE} ]; then
  sudo mkdir -p /home/vagrant/drukmakori/src/main/java
  sudo cp -R /home/vagrant/drukmakori/images /home/vagrant/drukmakori/src/main/java
  sudo rm -R /home/vagrant/drukmakori/images
  cd /home/vagrant/drukmakori
  javac *.java
  java -cp . Main &

  touch ${FIRST_SSH_STATE_FILE}
else
  # If the application is already running, restart it
  pkill -f 'java -cp . Main'
  cd /home/vagrant/drukmakori
  java -cp . Main &
fi
