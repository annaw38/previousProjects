import { NavigationContainer } from '@react-navigation/native';
import { useEffect, useState } from 'react';

import BadgerTabs from './navigation/BadgerTabs';
import CS571 from '@cs571/mobile-client';
import PreferencesContext from '../contexts/PreferencesContext';

export default function BadgerNews(props) {

  const [prefs, setPrefs] = useState({});
  const [articles, setArticles] = useState([]);
  // console.log(props.articles)

  useEffect(() => {
    fetch("https://cs571api.cs.wisc.edu/rest/f25/hw8/articles", {
        headers: {
            "X-CS571-ID": CS571.getBadgerId()
        }
    })
    .then(res => res.json())
    .then(d => setArticles(d))
  }, []);
  
  // set all the tags as true by default 
  useEffect(() => {
    if (articles.length > 0) {
      const initialPrefs = {};
      articles.flatMap(article => article.tags).forEach(tag => {
        initialPrefs[tag] = true; 
      });
      setPrefs(initialPrefs);
    }
  }, [articles]);
  

  // console.log(articles)
  // if enabled then disable and vice versa
  function handleSwitch(tag) {
    setPrefs(prev => ({
      ...prev, 
       [tag]: !prev[tag]
    }));
  };
  

  return (
    <>
    <PreferencesContext.Provider value = {{prefs, handleSwitch}}>
      <NavigationContainer>
          <BadgerTabs articles = {articles} />
      </NavigationContainer>
    </PreferencesContext.Provider>
    </>
  );
}