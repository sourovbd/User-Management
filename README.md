# Backend development environment setup

``Install Oracle Java 17``
1. sudo apt update && sudo apt upgrade -y
2. sudo add-apt-repository ppa:linuxuprising/java -y
3. sudo apt update
4. sudo apt-get install oracle-java17-installer oracle-java17-set-default

another way:
1. sudo apt install libc6-i386 libc6-x32 curl -y
2. wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb
3. sudo dpkg -i jdk-17_linux-x64_bin.deb
4. sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-17/bin/java 1

To remove: sudo dpkg -r jdk-17