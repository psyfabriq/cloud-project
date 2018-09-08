#!/bin/bash

APPNAME="app-pfq-enterprise"
DESCRIPTION='Enterprise server'
ROOTFOLDER='/opt/pfq-app/'
APPFOLDER="app-enterprise" 
AFTERSTART="app-pfq-eureka.service" #networking.service
USER='webapp'
GROUP='spring'
DISTRO=$(lsb_release -i | cut -f 2-)
COMMAND=""

echo "###################################PREPAIR ALL PAKAGES############################################"

if [[  ${DISTRO} == "CentOS" ]]
then
    echo "CentOS";
    	COMMAND="yum install -y "
elif [[  ${DISTRO} == "Ubuntu" ]]
then
    echo "Ubuntu";
         COMMAND="apt-get install -y "
elif [[  ${DISTRO} == "Debian" ]]
then
    echo "Debian";
         COMMAND="apt-get install -y "        
else
    echo "Unsupported Operating System";
    echo "${DISTRO}"
    exit 1;
fi

if ! which mvn > /dev/null; then
   echo -e "Maven not found! Install? (y/n) \c"
   read
   if "$REPLY" = "y"; then
      sudo $COMMAND mvn
   fi
fi

echo "###################################INSTALL APP CONFIG SERVER############################################"


if [ -d "$ROOTFOLDER" ] 
then
    echo "Directory $ROOTFOLDER exists." 
else
    mkdir -p $ROOTFOLDER
    touch  $ROOTFOLDER/secret
fi

egrep "^$GROUP" /etc/group >/dev/null
if [ $? -eq 0 ]; then
	echo "$GROUP exists!"
else
	groupadd $GROUP		
fi

egrep "^$USER" /etc/passwd >/dev/null
if [ $? -eq 0 ]; then
	echo "$USER exists!"
else
	PASSWORD="$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 13 ; echo '')"	
	
	mkdir -p /home/$USER/
	useradd -d /home/$USER/ -g $GROUP -p $PASSWORD $USER
	echo "LOGIN = $USER" >> $ROOTFOLDER/secret
    echo "PASSWD = $PASSWORD" >> $ROOTFOLDER/secret
    
    ln -s $ROOTFOLDER /home/$USER/APP
      
    chown -R $USER:$GROUP /home/$USER/
    chmod -R 775 /home/$USER/
    
fi

if [ -d "${ROOTFOLDER}${APPFOLDER}" ] 
then
    echo "Directory ${ROOTFOLDER}${APPFOLDER} exists." 
    exit 1
else
    mvn install 
    
    LINE=$(find ./target -name 'app-pfq-*.jar' -exec echo {} \;)

    set -- "$LINE"
    IFS='/' read -a arr <<< "$LINE"
    STARTER="${arr[2]}"
    
   sudo mv target ${ROOTFOLDER}${APPFOLDER}

   chown -R $USER:$GROUP ${ROOTFOLDER}${APPFOLDER}
   chmod -R 775 ${ROOTFOLDER}${APPFOLDER}/
        
    touch  /etc/systemd/system/$APPNAME.service
    
    echo "[Unit]" >> /etc/systemd/system/$APPNAME.service
    echo "Description=$DESCRIPTION" >> /etc/systemd/system/$APPNAME.service
    echo "After=$AFTERSTART" >> /etc/systemd/system/$APPNAME.service
    echo "          " >> /etc/systemd/system/$APPNAME.service
    echo "[Service]" >> /etc/systemd/system/$APPNAME.service
    echo "User=$USER" >> /etc/systemd/system/$APPNAME.service
    echo "ExecStart=$ROOTFOLDER$APPFOLDER/$STARTER" >> /etc/systemd/system/$APPNAME.service
    echo "SuccessExitStatus=143" >> /etc/systemd/system/$APPNAME.service
    echo "WorkingDirectory=$ROOTFOLDER$APPFOLDER/" >> /etc/systemd/system/$APPNAME.service
    echo "          " >> /etc/systemd/system/$APPNAME.service
    echo "[Install]" >> /etc/systemd/system/$APPNAME.service
    echo "WantedBy=multi-user.target" >> /etc/systemd/system/$APPNAME.service
fi