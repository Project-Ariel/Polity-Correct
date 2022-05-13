import json
from datetime import date
import urllib

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

import traceback
import urllib3
import xmltodict

polityCorrect_firebase_json_info = {
    "type": "service_account",
    "project_id": "polity-correct",
    "private_key_id": "6c3c0fd85b452f925d13ccc7a69861d00f5ba501",
    "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCyXfUElsatWLOR\nSEYEbISdEp1N1U8jZT5W6/JLENVCV0FtB5ZaW3WdI53Wgdh7TIlYwgx4Oq33cZ5C\n1YZuLchKZnfk5KqoukdsedlDMieHGQp/OLDFwTPgqvACakAxCB88yycleW787TbM\n5NhprTBmXR27SZRKA3EXBrVcHENlBLLtbhAdWnjtDM9tXXU9b/wmzQh9AQiTKOyO\n1n+K8TkqMTxIOlhHrrivGh5up/Uu3h3bRwckMB3GVAwrTQ8SVuG+QxRh0Tfw1hjD\nApIvDJE43Lu//UnUX06bDtQ/FpZqbVmofvJMzV+2rAfY0FQn44JpZNeNPEtdzZMF\nxNSQJN77AgMBAAECggEAE2opuNGml4i/pvEsed/MAYLcr3On9QzYn8J38ZbH97wi\nqXj5cyVgZHXTgN1KUIVwr8ITdGTCudvao6C1TLncAskrhLUlJE9+Zb7PmMJVTh26\nKEXl6+iPOoWwsTqaeRvOtqHQRYJ1w78YR24jZkFNMt8w1Ml6BCJixWRcCkYjSdPt\nkLI3U5pv+jfO8Nsv+tyeXLtseVEnixKDiidue/p0zoyLxF2JP/2XAowsjIZNMIBM\n77M4SwTFJsJiVOmMsk/21PfedgMfhsyX6DdsdSOanrDh6+n7eAnhFlbGmDbCr0MI\n6TJrChqY5KFG0tA35NIdTREHTGfJrK24IEapyhVOgQKBgQD4Shc9UnjnTQ23cZcj\nXlt1ZPQWVVh6vYV9cjfJNWD6f6MTX1PEdAHLWGzVSxavnt6lndxrDTORxGnyFlD2\nEudjTTEcX6JpZ1U8axpqiYucPeDYOtsZ9UzJ8IAVRuUvDI4/0gs7x+t8lbUnI2If\nyCNhVHy2s8iTF5/IS+xLnlROewKBgQC35/sdA2lhXovWO4qS5ZurOUl/he3SLFbi\nDUeAeJdgRjPbU0JIVmrptYUI8qipkZhi8OG8phDVvH1hAIP2Q511wms46hmBpuiR\nGFtmVr9a7MExBn3h7I2o9Ud+pHSyEJAgF3erhNdxt7g4gMhhlxsdyqBy6E5gY1dE\nL4t7sYUJgQKBgHSr/DeopX+rSaLGEctg74E+qyRDfoxOuoe0QFqgZq3PhIX56ZcL\nHDy9lIa1/jcQZHdWstjrHyyEK3TC39CCGZEkGMvIdL0q/XairYsajywgN6PkJEkS\nZ78M8GMPKWrub3m17eltjE7C2plFmzpsBZQa62+/E6VrGH46jxMJl3w5AoGAY0JF\n+obY3cDV4K8KP/bFakjlLnruvT1JN4DLUr/DlCbP3lS1ta1Ozw013Hp8Jel7QguZ\nCuTmuTWexjgtAUHFPsXMcU+IrL2VUJrA2h9/8GdQblF+p+2xF7wq9mN0yJa/81I3\nOM/eKHZsa7K2dFXGQPIYX2yawNNdSmQO+Jf7ogECfyFXnq6wdEGeNCXI9QAnFgeL\nQPxja10ClgBkQD1gnWWo3GutzBCxo9mbqNaZClkaUPtNZ90HpYusN/wrpPaoubU0\nXFf9JUx9tFfswq59JQgCefPGcrsBkLq95aoMuMI0KNTiPd20mkc1qlZoZvMklpw7\ncAPt/AwRyIb3U/oqHkA=\n-----END PRIVATE KEY-----\n",
    "client_email": "firebase-adminsdk-frkz1@polity-correct.iam.gserviceaccount.com",
    "client_id": "114466068948976677401",
    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    "token_uri": "https://oauth2.googleapis.com/token",
    "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-frkz1%40polity-correct.iam.gserviceaccount.com"
}

json_dump = json.dumps(polityCorrect_firebase_json_info)
json_object = json.loads(json_dump)

cred = credentials.Certificate(json_object)
firebase_admin.initialize_app(cred)
db = firestore.client()


def getxml(url_in, type=None):
    url = url_in
    http = urllib3.PoolManager()

    response = http.request('GET', url)

    try:
        if type == "count":
            data = int(str(response.data).split("'")[1])
        else:
            data = xmltodict.parse(response.data)
    except:
        print("Failed to parse xml from response (%s)" % traceback.format_exc())
    return data


def update_PoliticalGroups():
    data = getxml("https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Faction()?$filter=KnessetNum%20eq%2024")
    d = data['feed']['entry']
    group_props = []
    for group_index in range(len(d)):
        p = d[group_index]['content']['m:properties']
        name = p['d:Name']

        if p['d:IsCurrent']['#text'] == 'true':
            prop = {
                'group_name': f'{name}'
            }
            group_props.append(prop)

    ref = db.collection('PoliticalGroups')
    docs = ref.stream()

    group_firebase = []
    for doc in docs:
        group_firebase.append((doc.to_dict())['group_name'])

    new_group_name = []
    for i in group_props:
        new_group_name.append(i['group_name'])

    group_firebase_need_to_stay = []
    group_firebase_need_to_delete = []
    for group in group_firebase:
        if group in new_group_name:
            group_firebase_need_to_stay.append(group)
        else:
            # למחוק את השם הזה מהפיירבייס
            group_firebase_need_to_delete.append(group)

    docs = ref.stream()
    for doc in docs:
        if (doc.to_dict())['group_name'] in group_firebase_need_to_delete:
            ref.document(doc.id).delete()

    group_firebase_need_to_upload = []
    for group in new_group_name:
        if group not in group_firebase_need_to_stay:
            group_firebase_need_to_upload.append(group)
            ref.document().set({'group_name': f'{group}'})

        else:
            # תתעלם
            pass


def get_prop_status_table():
    statuses = {}

    data = getxml(f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Status")
    d = data['feed']['entry']
    for group_index in range(len(d)):
        p = d[group_index]['content']['m:properties']
        statusID = p['d:StatusID']['#text']
        statusDesc = p['d:Desc']

        statuses[statusID] = statusDesc

    return statuses


def update_Propositions():
    data_count = getxml(
        "https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Bill/$count/?$filter=KnessetNum%20eq%2024&?$format=json",
        "count")

    skip = 0
    propositions_props = []
    propositions_statuses = get_prop_status_table()
    while len(propositions_props) < 130:
        data = getxml(
            f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Bill()/?$filter=KnessetNum%20eq%2024&$skip={skip}")
        d = data['feed']['entry']
        for group_index in range(len(d)):
            if len(propositions_props) < 130:
                p = d[group_index]['content']['m:properties']
                billID = p['d:BillID']['#text']
                title = p['d:Name']
                category = p['d:SubTypeDesc']

                if type(p['d:SummaryLaw']) is str:
                    description = p['d:SummaryLaw']
                else:
                    description = None

                status = p['d:StatusID']['#text']

                if status == "118":
                    voted = True
                else:
                    voted = False

                prop = {
                    'odata_id': f'{billID}',
                    'category': f'{category}',
                    'description': f'{description}',
                    'status': f'{propositions_statuses[status]}',
                    'title': f'{title}',
                    'voted': voted
                }
                propositions_props.append(prop)

        skip += 100

    ref = db.collection('Propositions')
    docs = ref.stream()

    propositions_firebase = []
    for doc in docs:
        propositions_firebase.append(doc.to_dict()['odata_id'])

    new_propositions_id = []
    for i in propositions_props:
        new_propositions_id.append(i['odata_id'])

    propositions_firebase_need_to_stay = []
    propositions_firebase_need_to_delete = []
    for prop_id in propositions_firebase:
        if prop_id in new_propositions_id:
            propositions_firebase_need_to_stay.append(prop_id)
        else:
            propositions_firebase_need_to_delete.append(prop_id)
            # למחוק את השם הזה מהפיירבייס

    docs = ref.stream()
    for doc in docs:
        if (doc.to_dict())['odata_id'] in propositions_firebase_need_to_delete:
            ref.document(doc.id).delete()

    proposition_firebase_need_to_upload = []
    for prop_id in new_propositions_id:
        if prop_id not in propositions_firebase_need_to_stay:
            proposition_firebase_need_to_upload.append(prop_id)
        else:
            # תתעלם
            pass

    for p in propositions_props:
        if p["odata_id"] in proposition_firebase_need_to_upload:
            ref.document().set(p)


def update_118Propositions():
    data_count = getxml(
        "https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Bill/$count/?$filter=KnessetNum%20eq%2024&?$format=json",
        "count")

    skip = 0
    propositions_props = []
    propositions_statuses = get_prop_status_table()
    while len(propositions_props) < 6:
        data = getxml(
            f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Bill()/?$filter=KnessetNum%20eq%2024&$skip={skip}")
        d = data['feed']['entry']
        for group_index in range(len(d)):
            if len(propositions_props) < 6:
                p = d[group_index]['content']['m:properties']
                billID = p['d:BillID']['#text']
                title = p['d:Name']
                category = p['d:SubTypeDesc']

                if type(p['d:SummaryLaw']) is str:
                    description = p['d:SummaryLaw']
                else:
                    description = None

                status = p['d:StatusID']['#text']

                if status == "118":
                    voted = True
                else:
                    voted = False

                if voted:
                    is_in_vote_odata = getxml(
                        f"https://knesset.gov.il/Odata/Votes.svc/View_vote_rslts_hdr_Approved/$count/?$filter=sess_item_id%20eq%20{billID}&?$format=json",
                        "count")

                    is_in_ParliamentInfo_odata = getxml(
                        f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Bill()//$count/?$filter=BillID%20eq%20{billID}&?$format=json",
                        "count")

                    if is_in_ParliamentInfo_odata == is_in_vote_odata:
                        print()
                        print("voted", billID)

                        prop = {
                            'odata_id': f'{billID}',
                            'category': f'{category}',
                            'description': f'{description}',
                            'status': f'{propositions_statuses[status]}',
                            'title': f'{title}',
                            'voted': voted
                        }
                        propositions_props.append(prop)

        skip += 100

    ref = db.collection('Propositions')
    docs = ref.stream()

    propositions_firebase = []
    for doc in docs:
        propositions_firebase.append(doc.to_dict()['odata_id'])

    new_propositions_id = []
    for i in propositions_props:
        new_propositions_id.append(i['odata_id'])

    propositions_firebase_need_to_stay = []
    propositions_firebase_need_to_delete = []
    for prop_id in propositions_firebase:
        if prop_id in new_propositions_id:
            propositions_firebase_need_to_stay.append(prop_id)
        else:
            propositions_firebase_need_to_delete.append(prop_id)
            # לא צריך למחוק את השם הזה מהפיירבייס

    proposition_firebase_need_to_upload = []
    for prop_id in new_propositions_id:
        if prop_id not in propositions_firebase_need_to_stay:
            proposition_firebase_need_to_upload.append(prop_id)
        else:
            # תתעלם
            pass

    for p in propositions_props:
        if p["odata_id"] in proposition_firebase_need_to_upload:
            ref.document().set(p)


def get_person_to_position():
    persons = {}

    data_count = getxml(
        "https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_PersonToPosition/$count?$filter=KnessetNum%20eq%2024",
        "count")

    skip = 0
    i = 0
    c = 0
    while i < data_count:
        data = getxml(
            f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_PersonToPosition/?$filter=KnessetNum%20eq%2024&$skip={skip}")
        d = data['feed']['entry']
        for group_index in range(len(d)):
            i += 1
            p = d[group_index]['content']['m:properties']
            personsID = p['d:PersonID']['#text']
            personsPosition = p['d:PositionID']['#text']

            if personsPosition == '43' or personsPosition == '61':
                c += 1
                persons[personsID] = personsPosition
            else:
                if persons.get(personsID) == None:
                    persons[personsID] = personsPosition

        skip += 100

    return persons


def get_person_to_pg():
    persons = {}

    data_count = getxml(
        "https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_PersonToPosition/$count?$filter=KnessetNum%20eq%2024",
        "count")

    skip = 0
    i = 0
    c = 0
    while i < data_count:
        data = getxml(
            f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_PersonToPosition/?$filter=KnessetNum%20eq%2024&$skip={skip}")
        d = data['feed']['entry']
        for group_index in range(len(d)):
            i += 1
            p = d[group_index]['content']['m:properties']
            personsID = p['d:PersonID']['#text']

            try:
                personGroup = p['d:FactionID']['#text']
            except:
                personGroup = ""

            persons[personsID] = personGroup

        skip += 100

    return persons


def get_politicalGroups_id():
    data = getxml("https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Faction()?$filter=KnessetNum%20eq%2024")
    d = data['feed']['entry']
    group_props = {}
    for group_index in range(len(d)):
        p = d[group_index]['content']['m:properties']
        name = p['d:Name']
        p_id = p['d:FactionID']['#text']

        if p['d:IsCurrent']['#text'] == 'true':
            prop = {
                f'{p_id}': f'{name}'
            }
            group_props[name] = p_id

    ref = db.collection('PoliticalGroups')
    docs = ref.stream()

    group_firebase = {}
    for doc in docs:
        group_firebase[(doc.to_dict())['group_name']] = doc.id

    merge = {}
    for g in group_firebase:
        merge[group_props[g]] = group_firebase[g]

    return merge


def update_PoliticalMembers():
    data_count = getxml(
        "https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Person/$count?$filter=IsCurrent%20eq%20true",
        "count")

    skip = 0
    politicalMembers_props = []
    positions_statuses = get_person_to_position()
    politicalGroups_statuses = get_person_to_pg()
    politicalGroupsID = get_politicalGroups_id()
    i = 0

    while i < data_count:
        data = getxml(
            f"https://knesset.gov.il/Odata/ParliamentInfo.svc/KNS_Person/?$filter=IsCurrent%20eq%20true&$skip={skip}")
        d = data['feed']['entry']
        for group_index in range(len(d)):
            i += 1
            p = d[group_index]['content']['m:properties']
            personsID = p['d:PersonID']['#text']

            if positions_statuses.get(personsID) != None and positions_statuses.get(personsID) in {'61', '43'}:

                userName = f"{p['d:FirstName']} {p['d:LastName']}"
                mail = p['d:Email']
                g = p['d:GenderDesc']
                if g == 'זכר':
                    gender = 1
                else:
                    gender = 0
                userType = 'parliament'
                if politicalGroups_statuses.get(personsID) == "":
                    key_pg = ""
                else:
                    key_pg = politicalGroupsID[politicalGroups_statuses.get(personsID)]
                yearOfBirth = -1
                try:
                    password = mail[0] + mail[1] + "123456"
                except:
                    mail = p['d:Email']['#text']
                    password = mail[0] + mail[1] + "123456"

                person = {
                    'gender': f'{gender}',
                    'key_pg': f'{key_pg}',
                    'mail': f'{mail}',
                    'password': f'{password}',
                    'userName': f'{userName}',
                    'userType': f'{userType}',
                    'yearOfBirth': yearOfBirth
                }

                politicalMembers_props.append(person)

        skip += 100

    ref = db.collection('userTest')
    docs = ref.stream()

    politicalMembers_firebase = []
    for doc in docs:
        if doc.to_dict()['userType'] == 'parliament':
            politicalMembers_firebase.append(doc.to_dict()['mail'])

    new_politicalMembers_mail = []
    for i in politicalMembers_props:
        new_politicalMembers_mail.append(i['mail'])

    politicalMembers_firebase_need_to_stay = []
    politicalMembers_firebase_need_to_delete = []
    for member_id in politicalMembers_firebase:
        if member_id in new_politicalMembers_mail:
            politicalMembers_firebase_need_to_stay.append(member_id)
        else:
            # למחוק את השם הזה מהפיירבייס
            politicalMembers_firebase_need_to_delete.append(member_id)

    docs = ref.stream()
    for doc in docs:
        if (doc.to_dict())['mail'] in politicalMembers_firebase_need_to_delete:
            ref.document(doc.id).delete()

    proposition_firebase_need_to_upload = []
    for member_id in new_politicalMembers_mail:
        if member_id not in politicalMembers_firebase_need_to_stay:
            proposition_firebase_need_to_upload.append(member_id)
        else:
            # תתעלם
            pass

    for p in politicalMembers_props:
        if p["mail"] in proposition_firebase_need_to_upload:
            ref.document().set(p)

def get_members_votes_exist_in_firebase():
    ref = db.collection('MembersVotes')
    docs = ref.stream()

    members_votes_firebase = []
    for doc in docs:
        if doc.to_dict()['odata_id'] not in members_votes_firebase:
            members_votes_firebase.append(doc.to_dict()['odata_id'])

    return members_votes_firebase


def get_all_voted_prop_in_firebase():
    ref = db.collection('Propositions')
    docs = ref.stream()

    voted_prop_firebase = []
    for doc in docs:
        if doc.to_dict()['voted']:
            if doc.to_dict()['odata_id'] not in voted_prop_firebase:
                voted_prop_firebase.append(doc.to_dict()['odata_id'])

    return voted_prop_firebase


def get_PM_name_to_id():
    ref = db.collection('Users')
    docs = ref.stream()

    members_name_to_id_firebase = {}
    for doc in docs:
        if doc.to_dict()['userType'] == "parliament":
            members_name_to_id_firebase[doc.to_dict()['userName']] = doc.id

    return members_name_to_id_firebase


def get_prop_odata_id_to_prop_firebase_id():
    ref = db.collection('Propositions')
    docs = ref.stream()

    prop_odata_id_to_prop_firebase_id = {}
    for doc in docs:
        prop_odata_id_to_prop_firebase_id[doc.to_dict()['odata_id']] = doc.id

    return prop_odata_id_to_prop_firebase_id


def update_MembersVotes():
    odata_id_are_updated = get_members_votes_exist_in_firebase()
    odata_id_are_voted = get_all_voted_prop_in_firebase()
    prop_keys = get_prop_odata_id_to_prop_firebase_id()
    choice_to_string = {1: "agreement", 2: "against", 3: "abstain"}
    members_name = get_PM_name_to_id()

    s = []
    votes = []

    for prop in odata_id_are_voted:
        if prop not in odata_id_are_updated:
            odata_id = prop
            proposition_key = prop_keys[prop]
            data = getxml(
                f"https://knesset.gov.il/Odata/Votes.svc/View_vote_rslts_hdr_Approved?$filter=sess_item_id%20eq%20{prop}")
            d = data['feed']['entry']
            # for group_index in range(len(d)):
            p = d['content']['m:properties']
            vote_id = p['d:vote_id']['#text']

            data_vote = getxml(
                f"https://knesset.gov.il/Odata/Votes.svc/vote_rslts_kmmbr_shadow?$filter=vote_id%20eq%20{vote_id}")
            d_vote = data_vote['feed']['entry']

            for group_index_vote in range(len(d_vote)):
                v = d_vote[group_index_vote]['content']['m:properties']

                vote_res = v['d:vote_result']['#text']

                if int(vote_res) in choice_to_string.keys():
                    choice = choice_to_string[int(vote_res)]
                else:
                    choice = "abstain"

                flag = False
                try:
                    flag = True
                    vote_member_name = v['d:kmmbr_name']
                    user_id = members_name[vote_member_name]
                except:
                    flag = False
                    user_id = None
                    if f"{v['d:kmmbr_name']}" not in s:
                        s.append(f"{v['d:kmmbr_name']}")

                if flag:
                    vote = {
                        'odata_id': f'{odata_id}',
                        'proposition_key': f'{proposition_key}',
                        'choice': f'{choice}',
                        'user_id': f'{user_id}'
                    }
                    votes.append(vote)



    ref = db.collection('MembersVotes')

    for v in votes:
        ref.document().set(v)
if __name__ == '__main__':
    print("update_PoliticalGroups")
    update_PoliticalGroups()
    print("update_Propositions")
    update_Propositions()
    print("update_118Propositions")
    update_118Propositions()
    print("update_PoliticalMembers")
    # update_PoliticalMembers()
    print("update_MembersVotes")
    # update_MembersVotes()

    db = firestore.client()
    ref2 = db.collection('UpdateTracking')
    ref2.document(str(date.today())).set({'done': 'yes'})
