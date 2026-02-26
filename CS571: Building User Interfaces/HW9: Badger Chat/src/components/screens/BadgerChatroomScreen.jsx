import { StyleSheet, Text, Alert, View, Modal, Pressable, Button, TextInput, KeyboardAvoidingView} from "react-native";
import { FlatList } from 'react-native';
import { useEffect, useState } from 'react';
import BadgerChatMessage from "../helper/BadgerChatMessage"

function BadgerChatroomScreen(props) {
    const [messages, setMessages] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [title, setTitle] = useState("");
    const [body, setBody]= useState("");
    const [isDisabled, setIsDisabled] = useState(true);

    // refresh the screen 
    function refresh() {
        setIsLoading(true);
        props.getMessages().then(msgs => {
            setMessages(msgs);
            setIsLoading(false);
        });
    }

    useEffect(() => {
        refresh();
    }, [])

    // handle the user cancelling post 
    function handleCancel() {
        setTitle("");
        setBody("");
        setModalVisible(!modalVisible);
    }

    // handle the user adding a post
    function handleAddPost() {
        props.createPost(title, body).then(() => {
            setTitle("");
            setBody("");
            setModalVisible(!modalVisible);
            // refresh();
            props.triggerRefresh();
        });
    }
    
    // handle the user deleting a post 
    function handleDeletePost(msgId) {
        props.deletePost(msgId).then(() => {
            // Alert.alert("Successfully Deleted!", "Successfully deleted the post")
            props.triggerRefresh();
        });
    }

    // disable create post button when title and body are empty
    useEffect(() => {
        if (!title || !body){ 
            setIsDisabled(true);
        }else{
            setIsDisabled(false);
        }
    }, [title, body])

    // if guest then don't show add post button
    if (props.isGuest) {
        return <View>
            <FlatList
                data = {messages}
                keyExtractor={(msg) => msg.id.toString()}
                onRefresh={refresh}
                refreshing={isLoading}
                renderItem={({item}) => (<BadgerChatMessage username = {props.username} isGuest = {props.isGuest} deletePost = {(msgId) => handleDeletePost(msgId)} {...item}/>)} 
                contentContainerStyle={{ paddingBottom: 80 }}>
            </FlatList>
        </View>
    } else {
    return <View>
        <FlatList
            data = {messages}
            keyExtractor={(msg) => msg.id.toString()}
            onRefresh={refresh}
            refreshing={isLoading}
            renderItem={({item}) => (<BadgerChatMessage username = {props.username} deletePost = {(msgId) => handleDeletePost(msgId)} {...item}/>)} 
            contentContainerStyle={{ paddingBottom: 80 }}>
        </FlatList>
        <Modal
            animationType="slide"
            transparent={true}
            allowSwipeDismissal={true} 
            presentationStyle="pageSheet" 
            visible={modalVisible}
            onRequestClose={() => {
                setModalVisible(!modalVisible);
            }}>
        
            <KeyboardAvoidingView style={styles.centeredView} behavior="padding"> 
                <KeyboardAvoidingView style={styles.modalView} behavior="padding">
                    <Text style = {styles.modalText}>Create A Post</Text>
                    <Text style = {styles.modalText}>Title</Text>
                    <TextInput
                        style={styles.titleInput}
                        onChangeText={(t) => setTitle(t)}
                        value={title}
                        accessibilityLabel="Title"
                    />
                    <Text style = {styles.modalText}>Body</Text>
                    <TextInput
                        multiline = {true}
                        style={styles.bodyInput}
                        onChangeText={(t) => setBody(t)}
                        value={body}
                        accessibilityLabel="Body"
                    />
                    <KeyboardAvoidingView style = {styles.buttonContainer} behavior="padding">
                        <Button color="crimson" title="CREATE POST" disabled = {isDisabled} onPress={() => handleAddPost()} />
                        <Button color="gray" title="CANCEL" onPress={() => handleCancel()} />
                    </KeyboardAvoidingView>
                </KeyboardAvoidingView>
            </KeyboardAvoidingView>
        </Modal>
        <Pressable style = {styles.button} onPress={() => setModalVisible(!modalVisible)}>
            <Text style = {{color: "white"}}>ADD POST</Text>
        </Pressable>
    </View>
}}

const styles = StyleSheet.create({
    centeredView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    modalView: {
        width: "90%",   
        maxHeight: "80%",
        margin: 10,
        alignContent: "stretch",
        backgroundColor: 'white',
        borderRadius: 20,
        padding: 20,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
    },
    button: {
        position: "absolute",
        bottom: 0,
        left: 0,
        right: 0,
        backgroundColor: "crimson",
        paddingVertical: 15,
        alignItems: "center",
    },
    buttonContainer: {
        flexDirection: 'row', 
        justifyContent: 'center', 
        alignItems: 'center', 
        padding: 10,
    },
    modalText: {
        fontSize: 18, 
        marginBottom: 15,
        textAlign: "left",
    },
    titleInput: {
        alignSelf: "flex-start", 
        height: 40,
        width: 300,
        left: 0,
        margin: 12,
        borderWidth: 1,
        padding: 10,
    },
    bodyInput: {
        alignSelf: "flex-start", 
        textAlignVertical: 'top',
        height: 100,
        width: 300,
        left: 0,
        margin: 12,
        borderWidth: 1,
        padding: 10,
    },
});

export default BadgerChatroomScreen;