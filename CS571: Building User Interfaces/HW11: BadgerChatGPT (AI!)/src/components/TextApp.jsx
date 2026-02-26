import React, { useEffect, useRef, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { BeatLoader } from 'react-spinners';

import TextAppMessageList from './TextAppMessageList';
import Constants from '../constants/Constants';
import useStorage from '../useStorage';

function TextApp(props) {

    // Set to true to block the user from sending another message
    const [isLoading, setIsLoading] = useState(false);

    const [messages, setMessages] = useStorage("messages", []);
    const inputRef = useRef();
    const hasMounted = useRef(false);

    /**
     * Called when the TextApp initially mounts.
     */
    async function handleWelcome() {
        setMessages([{
            role: Constants.Roles.Developer, 
            content: props.persona.prompt
        }]);
        addMessage(Constants.Roles.Assistant, props.persona.initialMessage);
    }

    /**
     * Called whenever the "Send" button is pressed.
     * @param {Event} e default form event; used to prevent from reloading the page.
     */
    async function handleSend(e) {
        e?.preventDefault();
        const input = inputRef.current.value?.trim();
        setIsLoading(true);
        if(input) {
            addMessage(Constants.Roles.User, input);
            inputRef.current.value = "";

            const res = await fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw11/completions-stream`, {
                method: "POST",
                headers: {
                    "X-CS571-ID": CS571.getBadgerId(),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify([...messages, {
                    role: Constants.Roles.User, 
                    content: input
                }])
            })
            // read in chunks from a stream
            const reader = res.body.getReader();
            const decoder = new TextDecoder("utf-8");
            addMessage(Constants.Roles.Assistant, "");

            let done = false; 
            let concatStr = "";
            while (!done) {
                const respObj = await reader.read();
                // returns bits and bytes
                const value = respObj.value;
                // if done as true may/may not have value assoc w/ it 
                done = respObj.done;
                if (value) { 
                    // console.log(value);
                    const chunk = decoder.decode(value, {stream: true});
            
                    // keep strings with values/content in them
                    let lines = chunk.split('\n').filter(s=>s);
                    for (let line of lines) {
                        const deltaObj = JSON.parse(line);
                        // concatStr += deltaObj.delta;
                        const delta = deltaObj.delta || "";
                        appendToLastAssistantMessage(delta);

                    }   
                }
            }          
        }
        setIsLoading(false);
    }

    /**
     * Adds a message to the ongoing TextAppMessageList
     * 
     * @param {string} role The role of the message; either "user", "assistant", or "developer"
     * @param {*} content The content of the message
     */
    function addMessage(role, content) {
        setMessages(o => [...o, {
            role: role,
            content: content
        }]);
    }

    /**
     * Runs on refresh where the user is welcomed to BadgerChatGPT or the previous conversation is loaded
     */
    useEffect(() => {
        if (!hasMounted.current) {
            hasMounted.current = true;
            const saved = localStorage.getItem("messages");
            if (!saved || JSON.parse(saved).length === 0) {
                handleWelcome();
            }
        }
    }, []);

    function appendToLastAssistantMessage(delta) {
        setMessages(prev => {
            const copy = [...prev];
            const last = copy[copy.length - 1];

            if (last && last.role === Constants.Roles.Assistant) {
                copy[copy.length - 1] = {
                    ...last,
                    content: last.content + delta
                };
            }

            return copy;
        });
    }

    /**
     * Runs if new chat button is pressed or if persona changes
     */
    useEffect(() => {
        if (props.isResetNeeded) {
            handleWelcome();
        }
    }, [props.isResetNeeded]);

    return (
        <div className="app">
            <TextAppMessageList messages={messages}/>
            {isLoading ? <BeatLoader color="#36d7b7"/> : <></>}
            <div className="input-area">
                <Form className="inline-form" onSubmit={handleSend}>
                    <Form.Control
                        ref={inputRef}
                        style={{ marginRight: "0.5rem", display: "flex" }}
                        placeholder="Type a message..."
                        aria-label='Type and submit to send a message.'
                    />
                    <Button type='submit' disabled={isLoading}>Send</Button>
                </Form>
            </div>
        </div>
    );
}

export default TextApp;
