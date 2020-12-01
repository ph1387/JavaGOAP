# JavaGOAP
An implementation of the Goal Oriented Action Planning (GOAP) AI-system in Java. 
Creator: P H, ph1387@t-online.de 

---

## Overview
This library is heavily influenced by Brent Owens [Goal Oriented Action Planning for a Smarter AI](https://gamedevelopment.tutsplus.com/tutorials/goal-oriented-action-planning-for-a-smarter-ai--cms-20793) and Jeff Orkins [Applying Goal-Oriented Action Planning to Games](http://alumni.media.mit.edu/~jorkin/GOAP_draft_AIWisdom2_2003.pdf).
The GOAP system consists of five major parts:

 1. GoapAgent - the main component of the system representing a single agent.
 2. GoapPlanner - generates the final Queue of GoapActions that must be taken in order to fulfill a GoalState.
 3. GoapState - a state which influences either the current state of the Unit or its actions. This can either be a World- or a GoalState depending on the use case of the state itself.
 4. GoapUnit - a wrapper for the Unit that is going to take actions, used by the GoapAgent.
 5. GoapAction - a single action that can be taken by a GoapUnit.
 
The GoapAgent, GoapAction and the GoapState require you to directly extend / use them, whereas the rest of the classes can be swapped as desired. After creating the Agent and assigning a Unit the update() function of it has to be called continuously in order for it to function. The GoapUnit can be implemented as desired. Either subclasses with additional functionalities like immediately changing to a GoalState or with the given Interface (IGoapUnit) and only the bare bone functions.

A GoapAgent is defined by it surrounding (WorldState) and by the goal that it is trying to achieve (GoalState). Certain actions with preconditions and effects (GoapActions) can be taken to get to a / the GoalState(s). Each action taken influences the current WorldState and therefore the possible actions taken after it. I.e. the goal of eating a cookie requires the Unit to have a cookie in its hands (precondition) therefore queuing a action taking a cookie in its hand (effect) before it:

```java
WorldState: CookieInHand = false
GoalState: EatingCookie = true

AvailableActions:
	TakeCookieInHand 
		- precondition: CookieInHand = false
		- effect: CookieInHand = true
	EatCookie
		- precondition: CookieInHand = true
		- effect: EatingCookie = true
		- effect: CookieInHand = false

Sequence:
	TakeCookieInHand -> EatCookie
```
Different actions can have multiple effect and preconditions that must be matched in order to fulfill set goals. If the most important one can not be met by a sequence of GoapActions, then the next one is tried until either a goal can be reached or none remains. If more than one possible way are found the one with the lesser cost is chosen.

## References
 - [Goal Oriented Action Planning for a Smarter AI](https://gamedevelopment.tutsplus.com/tutorials/goal-oriented-action-planning-for-a-smarter-ai--cms-20793)
 - [Applying Goal-Oriented Action Planning to Games](http://alumni.media.mit.edu/~jorkin/GOAP_draft_AIWisdom2_2003.pdf)
 - [Javadoc](https://ph1387.github.io/JavaGOAP/index.html)
 - [How to use](https://github.com/ph1387/JavaGOAP/wiki)

## License
MIT [license](https://github.com/ph1387/JavaGOAP/blob/master/LICENSE.txt)