#!/usr/bin/sh

echo "compilando el proyecto"
mvn clean package -DskipTests=true

echo "creando directorio localops en bin"
mkdir $DEV_HOME/bin/localops

echo "moviendo jar a bin"
cp target/localops-0.0.1-SNAPSHOT.jar $DEV_HOME/bin/localops

echo "creando lanzador lo.sh"
if test -f "$DEV_HOME/bin/localops/lo.sh"; then
    rm $DEV_HOME/bin/localops/lo.sh
fi
touch $DEV_HOME/bin/localops/lo.sh
echo '#!/usr/bin/sh' > $DEV_HOME/bin/localops/lo.sh
echo 'java -jar $LOCALOPS_HOME/localops-0.0.1-SNAPSHOT.jar $*' >> $DEV_HOME/bin/localops/lo.sh
chmod +x $DEV_HOME/bin/localops/lo.sh

echo "creando enlace simbolico lo"
if test -f "$DEV_HOME/bin/localops/lo"; then
    rm "$DEV_HOME/bin/localops/lo"
fi
ln -s $DEV_HOME/bin/localops/lo.sh $DEV_HOME/bin/localops/lo