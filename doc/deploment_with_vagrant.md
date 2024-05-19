To make it work, you need:
- Vagrant
- A supported virtual machine (tested with VirtualBox, they also recommend it)
- For Windows, an X11 server (e.g., Xming)

To start:
1. Navigate to the directory where the Vagrantfile is located.
2. Run `vagrant up` (wait for the boot process to complete, may take 5-10 minutes).
3. Start an SSH session to the VM from the directory of the Vagrantfile: `vagrant ssh -- -X` (in PuTTY, enable X11 forwarding in by going to Connection -> SSH -> X11 and checking "Enable X11 forwarding").
4. If prompted, log in (username: vagrant, password: vagrant).
5. The game will start by default. If the X11 server is not running on your machine, start it and then run `bash /home/vagrant/check-and-start-java.sh` to start the game.