Működéshez kellenek:
    - Vagrant
    - Valamilyen támogatott vm (virtualbox-al tesztelve, ők is azt javasolják)
    - Windows esetén valamilyen x11 szerver (például. Xming)

Indítás:
    1. cd a mappába ahol van a Vagrantfile
    2. vagrant up (meg kell várni amég a boot befejeződik)
    3. ssh indítása a Vagrantfile mappájából a vmre -> vagrant ssh -- -X (x11-et továbbítva) powershell esetén, puttynál engedélyezni kell a x11 forwardingot (bal oldalt Connection->SSH->X11 és Enable x11 forwardingot bepipáni)
    4. ha kéri belépni (felhasználónév: vagrant, jelszó:vagrant)
    5. java -cp . Main és elindul a játék