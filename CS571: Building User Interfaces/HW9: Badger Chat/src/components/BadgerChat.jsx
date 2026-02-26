import { useEffect, useState } from 'react';
import { Alert } from "react-native";
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigationContainer } from '@react-navigation/native';

import CS571 from '@cs571/mobile-client'
import * as SecureStore from 'expo-secure-store';
import BadgerChatroomScreen from './screens/BadgerChatroomScreen';
import BadgerRegisterScreen from './screens/BadgerRegisterScreen';
import BadgerLoginScreen from './screens/BadgerLoginScreen';
import BadgerLandingScreen from './screens/BadgerLandingScreen';
import BadgerLogoutScreen from './screens/BadgerLogoutScreen';
import BadgerConversionScreen from './screens/BadgerConversionScreen';


const ChatDrawer = createDrawerNavigator();

export default function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [isRegistering, setIsRegistering] = useState(false);
  const [chatrooms, setChatrooms] = useState([]);
  const [username, setUsername] = useState("");
  const [refTrigger, setRefTrigger] = useState(0);
  const [isGuest, setIsGuest] = useState(false);

  useEffect(() => {
    // load chatrooms
    fetch("https://cs571api.cs.wisc.edu/rest/f25/hw9/chatrooms", {
        method: "GET",
        headers: {
          "X-CS571-ID": CS571.getBadgerId(),
        }
    })
    .then(res => res.json())
    .then(data => {
      // console.log(data);
      setChatrooms(data);
    })
  }, []);

  function handleLogin(username, pin) {
    // login to badgerchat
    if (!username || !pin){
      Alert.alert("You must provide both a username and pin!");
    } else{
      setUsername(username);
      fetch("https://cs571api.cs.wisc.edu/rest/f25/hw9/login", {
        method: "POST",
        headers: {
          "X-CS571-ID": CS571.getBadgerId(),
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            pin: pin
        })
      })
      .then(res => {
        // check if username is already taken
        if (res.status === 401) {
          Alert.alert("Incorrect Login", "Please try again!");
        }else if (res.status === 200) {
          return res.json();
        }
      })
      .then(d => {
        // console.log(d);
        // console.log(d.token)
        SecureStore.setItemAsync(username, d.token).then(() =>{
          Alert.alert("Login Successful", "Success!");
          setIsLoggedIn(true);
        })
      })
    }
  }

  function handleSignup(username, pin) {
    // register for badgerchat
    if (!username || !pin){
      Alert.alert("You must provide both a username and pin!");
    } else{
      setUsername(username);
      fetch("https://cs571api.cs.wisc.edu/rest/f25/hw9/register", {
        method: "POST",
        headers: {
          "X-CS571-ID": CS571.getBadgerId(),
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            pin: pin
        })
      })
      .then(res => {
        // check if username is already taken
        if (res.status === 409) {
          Alert.alert("Signup Failed", "Sorry, this username is taken!");
        }else if (res.status === 200) {
          return res.json();
        }
      })
      .then(d => {
        // console.log(d);
        // console.log(d.token)
        SecureStore.setItemAsync(username, d.token).then(() =>{
          Alert.alert("Signup Successful", "Success!");
          setIsLoggedIn(true);
          setIsGuest(false);
        })
      })
    }
  }

  // get messages for the current chatroom
  function getMessages(chatroom) {
    return fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw9/messages?chatroom=${chatroom}`, {
        method: "GET",
        headers: {
          "X-CS571-ID": CS571.getBadgerId(),
        }
    })
    .then(res => res.json())
    .then(msgs => {
        return msgs.messages; 
    });
  }

  // create a new post in a chatroom
  function createPost(chatroom, title, content) {
    return SecureStore.getItemAsync(username).then(result => {
      if (!result) {
        Alert.alert("Login Required!", "Login to make a post");
        return Promise.reject("No auth token");
      }
      // console.log(result)
      // post to chatroom
      return fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw9/messages?chatroom=${chatroom}`, {
          method: "POST",
          headers: {
            "X-CS571-ID": CS571.getBadgerId(),
            "Authorization": `Bearer ${result}`,
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
              title: title,
              content: content
          })
      })
      .then(res => {
          if (!res) return; 
          if (res.status === 200) {
            Alert.alert("Successfully posted!", "Successfully posted!");
            return res.json();
          } else if (res.status == 401){
            Alert.alert("Login Required!", "Login to make a post");
          } else if (res.status == 413) {
            Alert.alert("The format of the post is too long", "The title must be less than 128 characters or fewer and the body must be 1024 characters or fewer");
          }
      })
    }
  )}

  // delete a post made from chatroom
  function deletePost(msgId) {
    return SecureStore.getItemAsync(username).then(result => {
      if (!result) {
        Alert.alert("Login Required!", "Login to make a post");
        return Promise.reject("No auth token");
      }
      // Alert.alert("deleted")
      return fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw9/messages?id=${msgId}`, {
          method: "DELETE",
          headers: {
            "X-CS571-ID": CS571.getBadgerId(),
            "Authorization": `Bearer ${result}`,
          },
      })
      .then(res => {
          if (!res) return; 
          if (res.status === 200) {
            Alert.alert("Successfully deleted!", "Successfully deleted!");
            return res.json();
          } else if (res.status == 401){
            Alert.alert("Deletion Failed!", "Unable to delete another user's post");
          } 
      })
    }
  )}

  // logout from badgerchat
  function handleLogout() {
    SecureStore.deleteItemAsync(username).then(result => {
      Alert.alert("Logged Out", "Successfully logged out!");
      setUsername("");
      setIsLoggedIn(false);
      setIsGuest(false);
      setIsRegistering(false);
    })
  }

  // refresh trigger for after adding or deleting a post
  function triggerRefresh() {
    setRefTrigger(prev => prev + 1);
  }

  // handle conversion after guest signs up for badgerchat
  function handleConversion() {
    // Alert.alert("registering");
    setIsRegistering(true);
    setIsLoggedIn(false);
    setIsGuest(false);
  }

  if (isLoggedIn || isGuest) {
    return (
      <NavigationContainer>
        <ChatDrawer.Navigator>
          <ChatDrawer.Screen name="Landing" component={BadgerLandingScreen} />
          {
            chatrooms.map(chatroom => {
              return <ChatDrawer.Screen key={chatroom} name={chatroom}>
                {(props) => <BadgerChatroomScreen key={refTrigger} name={chatroom} username = {username} 
                getMessages = {() => getMessages(chatroom)} createPost={(title, body) => createPost(chatroom, title, body)} 
                deletePost = {(msgId) => deletePost(msgId)} triggerRefresh = {triggerRefresh} isGuest = {isGuest}/>}
              </ChatDrawer.Screen>
            })
          }
          {
            isLoggedIn ?
              // show logout screen if logged in 
              <ChatDrawer.Screen name="LogoutScreen" options={{title: "Logout"}}>
              {
                (props) => <BadgerLogoutScreen handleLogout = {handleLogout}/>
              } 
              </ChatDrawer.Screen> 
            : 
              // else show signup screen if guest
              <ChatDrawer.Screen name="SignupScreen" options={{title: "Signup"}}>
              {
                (props) => <BadgerConversionScreen handleConversion = {handleConversion}/>
              } 
              </ChatDrawer.Screen>
          }
        </ChatDrawer.Navigator>
      </NavigationContainer>
    );
  } else if (isRegistering) {
    return <BadgerRegisterScreen handleSignup={handleSignup} setIsRegistering={setIsRegistering} />
  } else {
    return <BadgerLoginScreen handleLogin={handleLogin} setIsRegistering={setIsRegistering} setIsGuest = {setIsGuest}/>
  }
}