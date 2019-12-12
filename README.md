# csc335-towerdef-lacernetic-crabtree1-davidgonzales-mv7757
csc335-towerdef-lacernetic-crabtree1-davidgonzales-mv7 created by GitHub Classroom

TO PLAY THE GAME: Open the TowerDefense.java file and run it. Once you are at the title screen, select a gamemode, single or multiplayer.

SINGLE PLAYER: Plays like  standard TD game. Users may only place towers during the place phase, they may not place towers during the attack
phase. Once the attack phase is over, the user can place more towers. If they run out of lives, the game is over. Each time a tower
attacks an enemy, money is added to the account. 

MULTIPLAYER: Must have a server and client on the same connection. The server picks the map for the game, and the server picks towers first.
Placing towers is a turn based system. The server side places, then the client and so on until the game is started. The server is the only
side able to start the game, but both sides are able to pause the game. FASTFORWARDING IN MULTIPLAYER IS NOT SUPPORTED. If the game ends,
the game will end for both players. The clint side of the game may be slightly behind the server on animations because of how the relationship
was designed and implemented. Only towers you place give money. Towers placed by other person will not give you money. Also, selling dose not work over the network. only works on your view
