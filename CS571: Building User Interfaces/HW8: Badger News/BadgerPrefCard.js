import { StyleSheet, View, Text, Switch} from "react-native";
import { useContext} from "react";
import PreferencesContext from "./src/contexts/PreferencesContext";

export default function BadgerPrefCard(props) {
    const {prefs, handleSwitch} = useContext(PreferencesContext);
    // check if the tag is enabled
    const enabled = prefs[props.tag] ?? true;
    // console.log(prefs)
    // console.log(props)
    return(
        <View style={[styles.card, props.style]}>
            {enabled === true ? 
                <Text>
                    Currently showing <Text style={{fontWeight: "bold"}}>{props.tag}</Text> articles
                </Text> :
                <Text> Currently NOT showing <Text style={{fontWeight: "bold"}}>{props.tag}</Text> articles</Text>
            }
            <Switch value={enabled} onValueChange={() => handleSwitch(props.tag)} trackColor={{false: 'gray', true: 'red'}} style={{alignSelf: 'center'}}  />

        </View>
    )
}

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
        justifyContent: 'center', 
        alignItems: 'center',
    }
})