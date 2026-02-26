import { Text, Pressable, Button, Alert, StyleSheet,} from "react-native";
import BadgerCard from "./BadgerCard"

function BadgerChatMessage(props) {
    const dt = new Date(props.created);
    // console.log(props)
    
    return <BadgerCard style={{ marginTop: 16, padding: 8, marginLeft: 8, marginRight: 8 }}>
        <Text style={{fontSize: 28, fontWeight: 600}}>{props.title}</Text>
        <Text style={{fontSize: 12}}>by {props.poster} | Posted on {dt.toLocaleDateString()} at {dt.toLocaleTimeString()}</Text>
        <Text></Text>
        <Text>{props.content}</Text>
        {/* add delete button to posts posted by current user */}
        {props.username === props.poster && !props.isGuest ? 
            <Pressable style = {styles.button} onPress={() => props.deletePost(props.id)}>
                    <Text style = {{color: "white"}}>DELETE POST</Text>
            </Pressable>
            : <Text></Text>
        }
    </BadgerCard>
}

const styles = StyleSheet.create({
    button: {
        left: 0,
        right: 0,
        backgroundColor: "red",
        paddingVertical: 10,
        alignItems: "center",
    },
});

export default BadgerChatMessage;