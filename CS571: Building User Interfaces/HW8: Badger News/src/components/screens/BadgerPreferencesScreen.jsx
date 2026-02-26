import {View} from "react-native";
import BadgerPrefCard from "../../../BadgerPrefCard";

function BadgerPreferencesScreen(props) {
    // console.log(props.articles)
    const tags = props.articles.flatMap(article => article.tags); //all tags 
    const distTags = [... new Set(tags)]; //set of article tags (no duplicates)
    // console.log(tags)
    // console.log(distTags)
    // const prefs = useContext(PreferencesContext);

    return <View>
        {distTags.map(tag => 
            <BadgerPrefCard key={tag} style = {{paddingTop:20}} tag = {tag}/>
        )}
    </View>
}

export default BadgerPreferencesScreen;