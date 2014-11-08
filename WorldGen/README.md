Game
====
So far, this branch consists of modeling the universe.

Everything extends the abstract Territory and is assigned a name, owner, location (in Cartesian space and in hierarchy), and resources.
The territories are (from lowest level to highest level): Continent, Planet, Star, StarSystem, Cluster, and Sector.

Only the Continent object really has a Resource object along with it, all others just recursively call down to the Continent level. This was deemed more efficient than constantly updating entries upon changes in resource number.
Resources are currently stored as simple files, but probably will be moved to a database at a later point.
