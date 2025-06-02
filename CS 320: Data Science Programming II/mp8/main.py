from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression, LogisticRegression
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import PolynomialFeatures, OneHotEncoder
from sklearn.preprocessing import StandardScaler
from sklearn.compose import make_column_transformer
import pandas as pd
from sklearn.model_selection import cross_val_score

#Goal:build a classifier that, given user and log data, can predict whether those users will be interested in our product.
xcols = ["past_purchase_amt", "age", "badge", "seconds"]
ycol = "y"

custom_trans = make_column_transformer(
    (PolynomialFeatures(degree=4, include_bias=False), ["past_purchase_amt", "age", "seconds"]),
    (OneHotEncoder(), ["badge"]),
)
m = Pipeline([
    ("transformers", custom_trans),
    ("std", StandardScaler()),
    ("lr", LogisticRegression(fit_intercept=False, max_iter=1000)),
])

# m = Pipeline([
#             ("pf", PolynomialFeatures(degree=4, include_bias=False)),
#             ("std", StandardScaler()),
#             ("lr", LogisticRegression(fit_intercept=False))
# ])
# m = LogisticRegression(fit_intercept=False)
   
class UserPredictor():
    def add_logs_as_features(users, logs):
        #adds log features as columns to user 
        #got help from office hours 
        #adapted from https://www.geeksforgeeks.org/python-pandas-dataframe-groupby/
        logs_data = logs.groupby(["user_id"]).agg(seconds = ('seconds', 'sum'))
        
        #adapted from https://www.geeksforgeeks.org/merge-two-pandas-dataframes-on-certain-columns/
        users = pd.merge(users, logs_data, on='user_id', how ="outer")
        #copied from https://www.geeksforgeeks.org/replace-nan-values-with-zeros-in-pandas-dataframe/
        users.fillna(0, inplace=True)
        return users
    
    def fit(self, train_users, train_logs, train_y):
        self.xcols = xcols
        self.ycol = ycol
        train_users = UserPredictor.add_logs_as_features(train_users, train_logs)
        m.fit(train_users[self.xcols], train_y[self.ycol])
        # scores = cross_val_score(m, train_users[self.xcols], train_y["y"])
        # print(f"AVG: {scores.mean()}, STD: {scores.std()}\n")

    def predict(self, test_users, test_logs):
        self.xcols = xcols
        self.y = ycol
        test_users = UserPredictor.add_logs_as_features(test_users, test_logs)
        return m.predict(test_users[self.xcols])
        