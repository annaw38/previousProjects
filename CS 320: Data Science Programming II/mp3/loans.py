import json
import csv
from io import TextIOWrapper
from zipfile import ZipFile, ZIP_DEFLATED
class Applicant:
    def __init__(self, age, race):
        self.age = age
        self.race = set()
        for r in race:
            if r in race_lookup.keys():
                self.race.add(race_lookup.get(r))
            continue
    def __repr__(self):
        return f"Applicant('{self.age}', {sorted(list(self.race))})"
    def lower_age(self):
        lower = self.age
        if "-" in lower:
            lower = int(lower.split("-")[0])
        elif ">" in lower:
            lower = int(lower[1:])
        elif "<" in lower:
            lower = int(lower[1:])
        return lower

    def __lt__(self, other):
        return self.lower_age() < other.lower_age()
    
class Loan:
    def __init__(self, values):
        self.loan_amount = self.float_extract(values["loan_amount"]) # write the float_extract method as specified below
        self.property_value = self.float_extract(values["property_value"])
        self.interest_rate = self.float_extract(values["interest_rate"])
        self.applicants = []
        #adapted from https://stackoverflow.com/questions/24204087/how-to-get-multiple-dictionary-values
        self.app1Race = [values[race] for race in ["applicant_race-1", "applicant_race-2", "applicant_race-3", "applicant_race-4", "applicant_race-5"]]
        self.app1Age = values["applicant_age"]
        self.app2Race = [values[race] for race in ["co-applicant_race-1", "co-applicant_race-2", "co-applicant_race-3", "co-applicant_race-4", "co-applicant_race-5"]]
        self.app2Age = values["co-applicant_age"]
        if self.app2Age == "9999":
            self.applicants = [Applicant(self.app1Age, self.app1Race)]
        else:
            self.applicants = [Applicant(self.app1Age, self.app1Race), Applicant(self.app2Age, self.app2Race)]
        
    def float_extract(self, string):
        if string == "NA" or string == "Exempt":
            return -1
        else:
            return float(string)
        
    def __str__(self):
        return f"<Loan: {round(self.interest_rate,2)}% on ${self.loan_amount} with {len(self.applicants)} applicant(s)>"
    
    def __repr__(self):
        return f"<Loan: {round(self.interest_rate,2)}% on ${self.loan_amount} with {len(self.applicants)} applicant(s)>"
    
    def yearly_amounts(self, yearly_payment):
        amt = self.loan_amount
        assert amt > 0 
        assert self.interest_rate > 0

        while amt > 0: 
            yield amt
            amt += ((self.interest_rate/float(100)) * amt)
            amt -= yearly_payment
            
with open("banks.json") as f:
    banks = json.load(f)  
    
class Bank:
    def __init__(self, name):
        self.name = name
        self.loan_list = []
        for bank in banks: 
            if bank["name"] == name:
                self.lei = bank["lei"]
                break
        with ZipFile("wi.zip", "r") as zf:
            with zf.open("wi.csv", "r") as f:
                reader = csv.DictReader(TextIOWrapper(f, encoding="utf-8"))
                for row in reader:
                    if(row["lei"] == self.lei):
                        #values = row;
                        self.loan_list.append(Loan(row))
                        
    def __getitem__(self, index):
        return self.loan_list[index]
    
    def __len__(self):
        return len(self.loan_list)

        
        

race_lookup = {
    "1": "American Indian or Alaska Native",
    "2": "Asian",
    "3": "Black or African American",
    "4": "Native Hawaiian or Other Pacific Islander",
    "5": "White",
    "21": "Asian Indian",
    "22": "Chinese",
    "23": "Filipino",
    "24": "Japanese",
    "25": "Korean",
    "26": "Vietnamese",
    "27": "Other Asian",
    "41": "Native Hawaiian",
    "42": "Guamanian or Chamorro",
    "43": "Samoan",
    "44": "Other Pacific Islander"
}

