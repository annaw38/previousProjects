import { Alert, Button, StyleSheet, Text, TextInput, KeyboardAvoidingView, View} from "react-native";
import { useState } from 'react';

function BadgerLoginScreen(props) {
    const [username, setUsername] = useState("");
    const [pin, setPin]= useState("");

    return <KeyboardAvoidingView style={styles.container} behavior="padding">
        <Text style={{ fontSize: 36 }}>BadgerChat Login</Text>
        <Text>{"\n"}Username</Text>
        <TextInput
          style={styles.input}
          onChangeText={(t) => setUsername(t)}
          value={username}
          accessibilityLabel="Username"
          autoCapitalize="none"
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
        <Button color="crimson" title="Login" onPress={() => {
            props.handleLogin(username, pin);
        }} />
        <Text>{"\n"}New Here?</Text>
        <View style = {styles.buttonContainer}>
            <Button color="grey" title="SIGNUP" onPress={() => props.setIsRegistering(true)} />
            <Button color="grey" title="CONTINUE AS GUEST" onPress={() => props.setIsGuest(true)} />
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

export default BadgerLoginScreen;