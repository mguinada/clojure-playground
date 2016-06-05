# Artificial intelligence

### Glossary for main concepts

Term                      | Description
--------------------------|------------------------------------------------------------------------------------
Agent                     | An **agent** is anything that can be viewed as perceiving its **environment** through **sensors** and acting upon that environment through **actuators**
Agent Function            | Mathematically speaking, we say that an agent’s behavior is described by the **agent function**
Agent Program             | The **agent function** for an artificial agent will be implemented by an **agent program**
Fully Observable          | If an **agent**’s sensors give it access to the complete state of the **environment** at each point in time, then we say that the task environment is **fully observable**
Goal-based agents         | Categorize **agents** that act to achieve their goals
Model-based Reflex Agents | Are **agents** that maintaini nternal state to track aspects of the world that are not evident in the current percept
Partially Observable      | When an **agent**'s sensors allow only partial **environment** state access
PEAS                      | Performance, Environment, Actuators, Sensors
Percept                   | **Percept** refers to the **agent**’s perceptual inputs at any given instant
Percept Sequence          | The **agent's** **percept sequence** is the complete history of everything the *agent* has ever perceived
Performance Measure       | If the sequence is desirable, then the agent has performed well. This notion of desirability is captured by a *performance measure* that evaluates any given sequence of *environment* states.
Rational Agent            | A *rational agent* is one that does the right thing
Simple reflex agents      | These agents respond directly to percepts, ignoring the rest of the percept history.
Task Environment          | The "problems" to which **rational agents** are the “solutions.” [AIM2010]
Utility-based agents      | **Utility-based agents** try to maximize their own expected performance

### Formal definitions

#### rational agent

For each possible percept sequence, a rational agent should select an action that is expected to maximize its performance measure,
given the evidence provided by the percept sequence and whatever built-in knowledge the agent has.


### Environment properties

**Fully observable** vs. **partially observable**: If an agent’s sensors give it access to the complete state of the environment at each point in time, then we say that the task environment is fully observable. A task environment is effectively fully observable if the sensors detect all aspects that are relevant to the choice of action; relevance, in turn, depends on the performance measure.

**Single agent** vs. **multiagent**: The distinction between **single-agent** and **multiagent** environments may seem simple enough. For example, an agent solving a crossword puzzle by itself is clearly in a single-agent environment, whereas an agent playing chess is in a two agent environment. There are, however, some subtle issues. First, we have described how an entity may be viewed as an agent, but we have not explained which entities must be viewed as agents. Does an agent A (the taxi driver for example) have to treat an object B (another vehicle) as an agent, or can it be treated merely as an object behaving according to the laws of physics, analogous to waves at the beach or leaves blowing in the wind? The key distinction is whether B’s behavior is best described as maximizing a performance measure whose value depends on agent A’s behavior. For example, in chess, the opponent entity B is trying to maximize its performance measure, which, by the rules of chess, minimizes agent A’s performance measure. Thus, chess is a **competitive** multiagent environment. In the taxi-driving environment, on the other hand, avoiding collisions maximizes the performance measure of all agents, so it is a partially **cooperative** multiagent environment. It is also partially com- petitive because, for example, only one car can occupy a parking space

**Deterministic** vs. **stochastic**. If the next state of the environment is completely determined by the current state and the action executed by the agent, then we say the environment is deterministic; otherwise, it is stochastic. In principle, an agent need not worry about uncertainty in a fully observable, deterministic environment. (In our definition, we ignore uncertainty that arises purely from the actions of other agents in a multiagent environment; thus, a game can be deterministic even though each agent may be unable to predict the actions of the others.) If the environment is partially observable, however, then it could appear to be stochastic. Most real situations are so complex that it is impossible to keep track of all the unobserved aspects; for practical purposes, they must be treated as stochastic.

We say an environment is **uncertain** if it is not fully observable or not deterministic.

A **nondeterministic** environment is one in which actions are characterized by their possible outcomes, but no probabilities are attached to them.

**Episodic** vs. **sequential**: In an episodic task environment, the agent’s experience is divided into atomic episodes. In each episode the agent receives a percept and then performs a single action. Crucially, the next episode does not depend on the actions taken in previous episodes (e.g. defective parts detector agent in an assembly line). Many classification tasks are episodic. In **sequential** environments, on the other hand, the current decision could affect all future decisions. (e.g. chess)

**Static** vs. **dynamic**: If the environment can change while an agent is deliberating, then we say the environment is dynamic for that agent; otherwise, it is static. If the environment itself does not change with the passage of time but the agent’s performance score does, then we say the environment is **semidynamic**.

**Discrete** vs. **continuous**: The discrete/continuous distinction applies to the state of the environment, to the way time is handled, and to the percepts and actions of the agent. (e.g chess for discrete, autonomous driving system for continous).

#### Sample classifications

Task Environment          | Observable | Agents | Deterministic | Episodic   | Static  | Discrete
--------------------------|------------|--------|---------------|------------|---------|------------
Crossword puzzle          | Fully      | Single | Deterministic | Sequential | Static  | Discrete
Chess with a clock        | Fully      | Multi  | Deterministic | Sequential | Semi    | Discrete
Poker                     | Partially  | Multi  | Stochastic    | Sequential | Static  | Discrete
Backgammon                | Fully      | Multi  | Stochastic    | Sequential | Static  | Discrete
Taxi driving              | Partially  | Multi  | Stochastic    | Sequential | Dynamic | Continuous
Medical diagnosis         | Partially  | Single | Stochastic    | Sequential | Dynamic | Continuous
Image analysis            | Fully      | Single | Deterministic | Episodic   | Semi    | Continuous
Part-picking robot        | Partially  | Single | Stochastic    | Episodic   | Dynamic | Continuous
Refinery controller       | Partially  | Single | Stochastic    | Sequential | Dynamic | Continuous
Interactive English tutor | Partially  | Multi  | Stochastic    | Sequential | Dynamic | Discrete

### Agent properties

The job of AI is to design an **agent program** that implements the **agent function** -  the mapping from percepts to actions. We assume this program will run on some sort of computing device with physical sensors and actuators—we callthis the **architecture**:

*agent = architecture + program*

Notice the difference between the **agent program**, which takes the current **percept** as input, and the **agent function**, which takes the entire percept history.
The agent program takes just the current percept as input because nothing more is available from the environment; if the agent’s actions need to depend on the entire percept sequence, the agent will have to remember the percepts.

#### The four kinds of agent programs

* Simple reflex agents
* Model-based reflex agents
* Goal-based agents
* Utility-based agents

#### Simple reflex agents

The simplest kind of **agent** is the **simple reflex agent**. These agents select actions on the basis of the current percept, ignoring the rest of the percept history.

Simple reflex behaviors occur even in more complex environments. Imagine yourself as the driver of the automated taxi. If the car in front brakes and its brake lights come on, then you should notice this and initiate braking. In other words, some processing is done on the visual input to establish the condition we call “The car in front is braking.” Then, this triggers some established connection in the agent program to the action “initiate braking.” We call such a connection a **condition–action rule**, written as:

```
if car-in-front-is-braking then initiate-braking.
```

#### Model-based reflex agents

The most effective way to handle partial observability is for the agent to *keep track of the part of the world it can’t see now*. That is, the agent should maintain some sort of **internal state** that depends on the percept history and thereby reflects at least some of the unobserved aspects of the current state.

Updating this internal state information as time goes by requires two kinds of knowledge to be encoded in the **agent program**. First, we need some information about how the world evolves independently of the agent—for example,that an overtaking car generally will be closer behind than it was a moment ago. Second, we need some information about how the agent’s own actions affect the world—for example, that when the agent turns the steering wheel clockwise, the car turns to the right, or that after driving for five minutes northbound on the freeway, one is usually about five miles north of where one was five minutes ago. This knowledge about “how the world works” whether implemented in simple Boolean circuits or in complete scientific theories—is called a **model** of the world. An agent that uses such a model is called a **model-based agent**.

#### Goal based agents

Knowing something about the current state of the environment is not always enough to decide what to do. For example, at a road junction, the taxi can turn left, turn right, or go straight on. The correct decision depends on where the taxi is trying to get to. In other words, as well as a current state description, the agent needs some sort of **goal** information that describes situations that are desirable—for example, being at the passenger’s destination.

#### Utility-based agents

Goals alone are not enough to generate high-quality behavior in most environments. For example, many action sequences will get the taxi to its destination (thereby achieving the goal) but some are quicker, safer, more reliable, or cheaper than others. Goals just provide a crude binary distinction between “happy” and “unhappy” states. A more general performance measure should allow a comparison of different world states according to exactly how happy they would make the agent. Because “happy” does not sound very scientific, economists and computer scientists use the term **utility** instead.

We have already seen that a performance measure assigns a score to any given sequence of environment states, so it can easily distinguish between more and less desirable ways of getting to the taxi’s destination. An agent’s **utility function** is essentially an internalization of the performance measure.

Partial observability and stochasticity are ubiquitous in the real world, and so, therefore, is decision making under uncertainty. Technically speaking, a rational utility-based agent chooses the action that maximizes the **expected utility** of the action outcomes.

### Learning agents

We have described agent programs with various methods for selecting actions. We have not, so far, explained how the agent programs come into being.

In his famous early paper, Turing (1950) considers the idea of actually programming his intelligent machines by hand.

He estimates how much work this might take and concludes “Some more expeditious method seems desirable.” The method he proposes is to build learning machines and then to teach them. In many areas of AI, this is now the preferred method for creating state-of-the-art systems.

A learning agent can be divided into four conceptual components:

* learning element
* performance element
* critic
* problem generator

The most important distinction is between the **learning element**, which is responsible for making improvements, and the **performance element**, which is responsible for selecting external actions.
The performance element is what we have previously considered to be the entire agent: it takes in percepts and decides on actions. The learning element uses feedback from the **critic** on how the agent is doing and determines how the performance element should be modified to do better in the future.

When trying to design an agent that learns a certain capability, the first question is not “How am I going to get it to learn this?” but “What kind of performance element will my agent need to do this once it has learned how?”

The **critic** tells the learning element how well the agent is doing with respect to a fixed **performance standard**. The critic is necessary because the percepts themselves provide no indication of the agent’s success. For example, a chess program could receive a percept indicating that it has checkmated its opponent, but it needs a **performance standard** to know that this is a good thing; the percept itself does not say so.

The last component of the learning agent is the **problem generator**. It is responsible for suggesting actions that will lead to new and informative experiences. The point is that if the performance element had its way, it would keep doing the actions that are best, given what it knows. But if the agent is willing to explore a little and do some perhaps suboptimal actions in the short run, it might discover much better actions for the long run. The problem generator’s job is to suggest these exploratory actions. This is what scientists do when they carry out experiments.

To make the overall design more concrete, let us return to the automated taxi example. The performance element consists of whatever collection of knowledge and procedures the taxi has for selecting its driving actions. The taxi goes out on the road and drives, using this performance element. The critic observes the world and passes information along to the learning element. For example, after the taxi makes a quick left turn across three lanes of traf- fic, the critic observes the shocking language used by other drivers. From this experience, the learning element is able to formulate a rule saying this was a bad action, and the performance element is modified by installation of the new rule. The **problem generator** might identify certain areas of behavior in need of improvement and suggest experiments, such as trying out the brakes on different road surfaces under different conditions.

### How the components of agent programs work

Roughly speaking, we can place the representations along an axis of increasing complexity and expressive power — **atomic**, **factored**, and **structured**.

In an **atomic representation** each state of the world is indivisible—it has no internal structure. e.g. State A, State B, ...

Now consider a higher-fidelity description for the same problem, where we need to be concerned with more than just atomic location in one city or another; we might need to pay attention to how much gas is in the tank, our current GPS coordinates, whether or not the oil warning light is working, how much spare change we have for toll crossings, what station is on the radio, and so on.
A **factored representation** splits up each state into a fixed set of **variables** or **attributes**, each of which can have a **value**.

For many purposes, we need to understand the world as having things in it that are related to each other, not just variables with values. For example, we might notice that a large truck ahead of us is reversing into the driveway of a dairy farm but a cow has got loose and is blocking the truck’s path. A factored representation is unlikely to be pre-equipped with the attribute TruckAheadBackingIntoDairyFarmDrivewayBlockedByLooseCow with value true or false. Instead, we would need a **structured representation**, in which objects such as cows and trucks and their various and varying relationships can be described explicitly.

As we mentioned earlier, the axis along which atomic, factored, and structured repre- sentations lie is the axis of increasing **expressiveness**. Roughly speaking, a more expressive representation can capture, at least as concisely, everything a less expressive one can capture, plus some more.

### Bibliography

Artificial Intelligence a Modern Approach, 2010, by Peter Norvig and Stuart J. Russel
