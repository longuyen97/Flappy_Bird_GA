# Genetic Flappy Bird

![](data/result-1.gif)

Neural network learns to play Flappy Bird. The algorithm is simple:
- Initialize a population of 15 birds. Each bird has a random brain with random parameters. A brain would look like following

<img src="data/network.png" width="450"></img>

Each parameter ``Wij`` will be generated randomly at the beginning. The input is the current velocity of the bird, distance 
between the bird and the next pipes, the location of the next pipes, etc. The last neuron of the network will indicates for each input
if the bird should jump.

Of course at the beginning, the brain's performance will be very bad. However, some are less worse than others.

- In the next step, the fitness of each brain will be evaluated. The fittest have a greater chance of reproduce and mating with 
other brains. To keep the gene pool diverse, some kind of mutation will be done like ``Wij +- eps``.

- After a while, the population's performance will become better and better.

# References
- [Thanks to @john525 for flappy bird starter code. I am not a good game developer :)](https://github.com/john525/Flappy-Bird-Clone)
- [Thanks to @Code-Bullet for the inspiration](https://www.youtube.com/watch?v=WSW-5m8lRMs&t=376s)