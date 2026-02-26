import { Pressable, StyleSheet, View, Image, Text} from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function BadgerNewsItemCard(props) {
    const navigation = useNavigation();
    function handlePress() {
        navigation.push('Article', {
            fullArticleId: props.fullArticleId,
            img: props.img,
            title: props.title
        });
    }
    return <Pressable onPress={handlePress}>
        <View style={[styles.card, props.style]}>
            <Image source={{uri: `https://raw.githubusercontent.com/CS571-F25/hw8-api-static-content/main/${props.img}`}}  style={{width: 375, height: 200}}/>
            <Text>{props.title}</Text>
        </View>
    </Pressable>
}
// style for card 
const styles = StyleSheet.create({
    card: {
        padding: 10,
        marginTop: 10,
        marginLeft: 5,
        marginRight: 5, 
        marginBottom: 10,
        elevation: 5,
        borderRadius: 10,
        backgroundColor: 'white',
        shadowOffset: {
          width: 4,
          height: 4,
        },
        shadowOpacity: 0.2,
        shadowRadius: 1,
    }
})