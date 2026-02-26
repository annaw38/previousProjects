import { createNativeStackNavigator } from "@react-navigation/native-stack";
import BadgerNewsScreen from "./BadgerNewsScreen";
import ArticleScreen from "./ArticleScreen";

const BadgerStack = createNativeStackNavigator();

function BadgerNewsStack(props) {

    return (
        <BadgerStack.Navigator screenOptions={{headerShown: true, headerTitleStyle: {fontSize:20}}}>
            <BadgerStack.Screen screenOptions={{title: "AllArticles"}} name="Articles">
                {() => <BadgerNewsScreen articles={props.articles}/>}
            </BadgerStack.Screen>
            <BadgerStack.Screen screenOptions={{title: "FullArticle"}} name="Article" component = {ArticleScreen} />
        </BadgerStack.Navigator>
    );
}
export default BadgerNewsStack;
