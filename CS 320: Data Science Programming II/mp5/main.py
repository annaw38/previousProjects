import pandas as pd
import flask
import time
from flask import Flask, request, jsonify
import json
import matplotlib
import matplotlib.pyplot as plt
matplotlib.use('Agg')
import io
import re

#source of the data is https://www.kaggle.com/datasets/arslaan5/explore-car-performance-fuel-efficiency-data?resource=download from Kaggle.com

app = Flask(__name__)
df = pd.read_csv("main.csv")
count = 0
countA = 0
countB = 0
num_subscribed = 0
# last_visit = 0
# addrs = []
addrs = {}

#copied from https://www.w3schools.com/python/ref_func_round.asp
df.round(5)

@app.route('/')
def home():
    global count
    global countA
    global countB
    #args = dict(flask.request.args)
    with open("index.html") as f:
        html = f.read()
    if count < 10:
        if count % 2 == 0:
            html = html.replace("{{REPLACEME}}", '<a href="/donate.html?from=A" style ="color:blue">donate</a>')
            countA += 1
        else: 
            html = html.replace("{{REPLACEME}}", '<a href="/donate.html?from=B" style ="color:green">donate</a>')
            countB += 1
    else:
        if countA >= countB:
            html = html.replace("{{REPLACEME}}", '<a href="/donate.html?from=A" style ="color:blue">donate</a>')
        else: 
            html = html.replace("{{REPLACEME}}", '<a href="/donate.html?from=B" style ="color:green">donate</a>')
    count += 1
    return html

@app.route('/browse.html')
def browse():
    html = df.to_html()
    return "<html><h1>Browse</h1>{}<html>".format(html)

@app.route('/browse.json')
def table():
    # global last_visit
    global addrs
    if flask.request.remote_addr in addrs: 
        if time.time() - addrs.get(flask.request.remote_addr) > 60:
            #update last visit
            addrs[flask.request.remote_addr] = time.time()
            #adapted from https://www.geeksforgeeks.org/use-jsonify-instead-of-json-dumps-in-flask/
            return jsonify(df.to_dict('records'))
        else:
            return flask.Response("<b>Retry after one minute.</b>",
                                  status=429,
                                  headers={"Retry-After": "60"})
    else:
        addrs[flask.request.remote_addr] = time.time()
        #copied from https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.to_dict.html
        return jsonify(df.to_dict('records'))

@app.route('/visitors.json')
def visitors():
    global addrs
    # copied from https://stackoverflow.com/questions/16819222/how-do-i-return-dictionary-keys-as-a-list-in-python
    ips = list(addrs.keys())
    #print(ips)
    return ips

@app.route('/donate.html')
def donate():
    global countA
    global countB
    with open("donate.html") as f:
        donatehtml = f.read()
    args = dict(flask.request.args)
    # print(args)
    if "A" in args.values():
        countA += 1
    elif "B" in args.values():
        countB += 1
    return donatehtml

@app.route('/email', methods=["POST"])
def email():
    global num_subscribed
    email = str(request.data, "utf-8")
    if len(re.findall(r"^\w+@\w+\.\D{3}$", email)) > 0: # 1
        with open("emails.txt", "a") as f: # open file in append mode
            f.write(email + '\n') # 2
        num_subscribed += 1
        return jsonify(f"thanks, your subscriber number is {num_subscribed}!")
    return jsonify(f"Try again with a valid email.") # 3

 
@app.route("/dashboard1.svg")
def plot1():
    args = dict(flask.request.args)
    if "mileagePlots" in args.values():
        fig, ax = plt.subplots(figsize=(10, 5))
        
        comboMPG = df["combination_mpg"]
        highMPG = df["highway_mpg"]
        cityMPG = df["city_mpg"]
        mpgs = [cityMPG, highMPG, comboMPG]
        
        plt.boxplot(mpgs)
        # adapted from https://www.geeksforgeeks.org/box-plot-in-python-using-matplotlib/
        ax.set_xticklabels(['City Mileage', 'Highway Mileage','Combination(City and Highway) Mileage'])
        ax.set_xlabel("Location")
        ax.set_ylabel("Fuel Efficiency in miles per gallon(mpg)")
        ax.set_title("Fuel Efficiency(mpg) at Various Locations")
        plt.tight_layout()
        
        plt.savefig("dashboard1-query.svg") 
        f = io.StringIO() 
        plt.savefig(f, format="svg")
        plt.close()

        return flask.Response(f.getvalue(), headers={"Content-type": "image/svg+xml"})
        
    else:    
        fig, ax = plt.subplots(figsize=(40, 5))
        
        make = df["make"]
        comboMPG = df["combination_mpg"]
        plt.bar(make, comboMPG)

        ax.set_xlabel("Car Make")
        ax.set_ylabel("Combination Fuel Efficiency(mpg)")
        ax.set_title("Combination Fuel Efficiency(mpg) of Various Car Manufactures")
        plt.tight_layout()
        plt.show()
        plt.savefig("dashboard1.svg")

        f = io.StringIO() 
        plt.savefig(f, format="svg")
        plt.close()

        return flask.Response(f.getvalue(), headers={"Content-type": "image/svg+xml"})
    
@app.route("/dashboard2.svg")
def plot2():
    fig2, ax2 = plt.subplots(figsize=(25,5))
    
    classes = df["class"]
    cityMPG = df["city_mpg"]
    plt.scatter(classes, cityMPG)
    
    ax2.set_xlabel("Car Class")
    ax2.set_ylabel("City Fuel Efficiency(mpg)")
    ax2.set_title("Fuel Efficiency in City Driving(mpg) of Various Car Classes")
    plt.tight_layout()
    plt.savefig("dashboard2.svg")
    
    f2 = io.StringIO() 
    plt.savefig(f2, format="svg")
    plt.close()
    
    return flask.Response(f2.getvalue(), headers={"Content-type": "image/svg+xml"})

@app.route("/dashboard3.svg")
def plot3():
    fig3, ax3 = plt.subplots(figsize=(10,5))
    
    hwMPG = df["highway_mpg"]
    plt.hist(hwMPG)
    ax3.set_xlabel("Highway Fuel Efficiency(mpg)")
    ax3.set_ylabel("Frequency of Cars")
    ax3.set_title("Trend of Fuel Efficiency in Highway Driving(mpg)")
    plt.tight_layout()
    plt.savefig("dashboard3.svg")
    
    f3 = io.StringIO() 
    plt.savefig(f3, format="svg")
    plt.close()
    
    return flask.Response(f3.getvalue(), headers={"Content-type": "image/svg+xml"})

if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, threaded=False) # don't change this line!

# NOTE: app.run never returns (it runs for ever, unless you kill the process)
# Thus, don't define any functions after the app.run call, because it will
# never get that far.
