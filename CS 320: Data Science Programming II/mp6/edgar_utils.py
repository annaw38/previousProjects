import re
import netaddr
import pandas as pd
from bisect import bisect

ips = pd.read_csv("ip2location.csv")
ips

def lookup_region(ipAddr):
    ipAddr = re.sub("[a-zA-Z]", "0", ipAddr)
    numIP = int(netaddr.IPAddress(ipAddr))
    ind = bisect(ips["high"], numIP)
    return ips.iloc[ind].region
    
class Filing:
    def __init__(self, html):
        dates = re.findall(r"((19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]))", html)
        self.dates = []
        for date in dates:
            self.dates.append(date[0])
        # self.dates = dates
        # print(self.dates)
        codes = re.findall(r"SIC=(\d+)",html)
        if len(codes) == 0:
            self.sic = None
        else:
            self.sic = int(codes[0])
        self.addresses = []
        for addr_html in re.findall(r'<div class="mailer">([\s\S]+?)</div>', html):
            lines = []
            for line in re.findall(r'<span class="mailerAddress">([\s\S]*?)</span>', addr_html):
                lines.append(line.strip())
            if lines:  
                self.addresses.append("\n".join(lines))
             
    def state(self):
        for addr in self.addresses:
            # match = re.findall(r'(?<!\w)(([A-Z]{2})\s{1}\d{5})', addr)
            #got help from TA/PM office hours
            match = re.findall(r'\W([A-Z]{2})\s*\d{5}', addr)
            if len(match) > 0:
                return match[0]
        return None