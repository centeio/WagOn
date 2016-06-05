package com.feup.lpoo.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Manager of game state
 * @author Carolina Centeio e Ines Proenca
 */
public class GameStateManager {
    private Stack<State> states; /**stack of game states*/

    /**
     * Constructor for GameStateManager.
     * <p>Inicializes states as an empty stack</p>
     */
    public GameStateManager(){
        states = new Stack<State>();
    }

    /**
     * Pushes new state to the top od the states stack
     * @param state state to add
     */
    public void push(State state){
        states.push(state);
    }

    /**
     * Removes the state at the top from the stack and disposes it
     */
    public void pop(){
        states.pop().dispose();
    }

    /**
     * Substitutes state at the top of the stack for a new one
     * @param state new state
     */
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Updates state at the top of the stack
     * @param dt time since last update
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * Draws state in sprite batch
     * @param sb sprite batch
     */
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
