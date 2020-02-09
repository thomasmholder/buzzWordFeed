from bs4 import BeautifulSoup
import requests
import sys

websites = "snowflake.com dosist.com samsara.com brex.com robinhood.com nuro.ai li.me flexport.com hpccsystems.com blackrock.com zoox.com tripactions.com simplebet.io aurora.tech databricks.com rubrik.com coda.io affirm.com ripple.com coinbase.com bumble.com faire.com cameo.com verkada.com amperity.com confluent.io scale.com thehive.ai thoughtspot.com drift.com ellevest.com ethoslife.com plaid.com outreach.io"

def printHeadings(url):
    page = BeautifulSoup(requests.get(url).text, "lxml")
    for a in page.find_all(["h1", "h2", "h3", "h4", "h5", "h6"]):
        sys.stdout.write(a.text.strip()+" ")
        text = a.text.encode('utf-8').strip().replace("\r"," ")
        text = text.replace("\n","")
        file = open("trainingdata.txt","a")
        file.write(text+" ")
        file.close()

for url in websites.split():
    print("|")
    print("|")
    print("|")
    print(url+"----------")
    printHeadings("http://"+url+"/")
