import { Alert, Button, StyleSheet, Text, TextInput, KeyboardAvoidingView, View } from "react-native";
import { useEffect, useState} from 'react';

function BadgerRegisterScreen(props) {
    const [username, setUsername] = useState("");
    const [pin, setPin]= useState("");
    const [confPin, setConfPin]= useState("");
    const [isDisabled, setIsDisabled] = useState(true);
    
    // check that the pin length is 7, conf pin matches pin and username exists then sets isdisabled 
    useEffect(() => {
        confPin.length === 7 && pin.length === 7 && confPin === pin && username ? setIsDisabled(false) : setIsDisabled(true)
    }, [pin, confPin, username]);

    return <KeyboardAvoidingView style={styles.container} behavior="padding">
        <Text style={{ fontSize: 36 }}>Join BadgerChat!</Text>
        <Text>{"\n"}Username</Text>
        <TextInput
            style={styles.input}
            onChangeText={(t) => setUsername(t)}
            value={username}
            accessibilityLabel="Username"
            autoCapitalize="none"
            autoComplete="off"
        />
        <Text>PIN</Text>
        <TextInput
            style={styles.input}
            onChangeText={(t) => setPin(t)}
            value={pin}
            accessibilityLabel="PIN"
            inputMode="numeric"
            keyboardType="number-pad"
            maxLength={7}
            returnKeyType="done" 
            secureTextEntry = {true}
        />
        <Text>Confirm PIN</Text>
        <TextInput
            style={styles.input}
            onChangeText={(t) => setConfPin(t)}
            value={confPin}
            accessibilityLabel="PIN"
            inputMode="numeric"
            keyboardType="number-pad"
            maxLength={7}
            returnKeyType="done" 
            secureTextEntry = {true}
        />
        {/* check if the pins match and has a pin */}
        {(!pin ? <Text style = {{"color": "red"}}>Please enter a pin.</Text> : 
            (pin !== confPin ? <Text style = {{"color": "red"}}>Pins do not match!</Text> :
            (pin.length !== 7 && confPin.length !== 7) ? <Text style = {{"color": "red"}}>A pin must be 7 digits.</Text> : <Text></Text>))}
        
        <View style = {styles.buttonContainer}>
            <Button color="crimson" title="Signup" disabled = {isDisabled} onPress={()=> props.handleSignup(username, pin)} />
            <Button color="grey" title="Nevermind!" onPress={() => props.setIsRegistering(false)} />
        </View>
    </KeyboardAvoidingView>;
}   

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    input: {
        height: 40,
        width: 250,
        margin: 12,
        borderWidth: 1,
        padding: 10,
    },
    buttonContainer: {
        flexDirection: 'row', 
        justifyContent: 'center', 
        alignItems: 'center', 
        padding: 10,
    },
});

export default BadgerRegisterScreen;