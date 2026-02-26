import { Text, ScrollView } from "react-native";
import { useContext} from "react";
import BadgerNewsItemCard from  "../../../BadgerNewsItemCard";
import PreferencesContext from "../../contexts/PreferencesContext";

function BadgerNewsScreen(props) {
    // console.log(props)
    const {prefs} = useContext(PreferencesContext);
    const visibleArticles = props.articles.filter(
        article => article.tags.every(tag => prefs[tag]) //every article shown must have all tags = true
    );


    // console.log(visibleArticles)
    
    return (visibleArticles && visibleArticles.length > 0 ? (
        <ScrollView>
            {visibleArticles.map(article => (
                <BadgerNewsItemCard key={article.id} {...article} style = {{paddingTop: 20}}/>
            ))}
        </ScrollView>
    ) :(<Text style={{paddingTop: 128, textAlign: "center", fontSize: 30}}>There are no articles that fit your preferences!</Text>))
}

export default BadgerNewsScreen;