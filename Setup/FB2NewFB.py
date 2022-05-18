import json
from datetime import date

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

client_firebase_json_info = "Here"

client_json_dump = json.dumps(client_firebase_json_info)
client_json_object = json.loads(client_json_dump)

client_cred = credentials.Certificate(client_json_object)
firebase_admin.initialize_app(client_cred)
client_db = firestore.client()

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

pc_json_dump = json.dumps(polityCorrect_firebase_json_info)
pc_json_object = json.loads(pc_json_dump)

pc_cred = credentials.Certificate(pc_json_object)
firebase_admin.initialize_app(pc_cred)
pc_db = firestore.client()


def update_MembersVotes():
    client_ref = client_db.collection('MembersVotes')

    pc_ref = pc_db.collection('MembersVotes')
    pc_docs = pc_ref.stream()

    for doc in pc_docs:
        try:
            choice = (doc.to_dict())['choice']
        except:
            choice = ""
        try:
            odata_id = (doc.to_dict())['odata_id']
        except:
            odata_id = ""
        try:
            proposition_key = (doc.to_dict())['proposition_key']
        except:
            proposition_key = ""
        try:
            user_id = (doc.to_dict())['user_id']
        except:
            user_id = ""

        MembersVotes = {
            'choice': f'{choice}',
            'odata_id': f'{odata_id}',
            'proposition_key': f'{proposition_key}',
            'user_id': f'{user_id}'
        }

        client_ref.document().set(MembersVotes)


def update_PoliticalGroups():
    client_ref = client_db.collection('PoliticalGroups')

    pc_ref = pc_db.collection('PoliticalGroups')
    pc_docs = pc_ref.stream()

    for doc in pc_docs:
        try:
            abbreviation = (doc.to_dict())['abbreviation']
        except:
            abbreviation = ""
        try:
            group_name = (doc.to_dict())['group_name']
        except:
            group_name = ""
        try:
            group_website = (doc.to_dict())['group_website']
        except:
            group_website = ""

        PoliticalGroups = {
            'abbreviation': f'{abbreviation}',
            'group_name': f'{group_name}',
            'group_website': f'{group_website}'
        }

        client_ref.document().set(PoliticalGroups)


def update_Propositions():
    client_ref = client_db.collection('Propositions')

    pc_ref = pc_db.collection('Propositions')
    pc_docs = pc_ref.stream()

    for doc in pc_docs:
        try:
            category = (doc.to_dict())['category']
        except:
            category = ""
        try:
            description = (doc.to_dict())['description']
        except:
            description = ""
        try:
            odata_id = (doc.to_dict())['odata_id']
        except:
            odata_id = ""
        try:
            status = (doc.to_dict())['status']
        except:
            status = ""
        try:
            title = (doc.to_dict())['title']
        except:
            title = ""
        try:
            voted = (doc.to_dict())['voted']
        except:
            voted = ""

        Propositions = {
            'category': f'{category}',
            'description': f'{description}',
            'odata_id': f'{odata_id}',
            'status': f'{status}',
            'title': f'{title}',
            'voted': f'{voted}'
        }

        client_ref.document().set(Propositions)


def update_Users():
    client_ref = client_db.collection('Users')

    pc_ref = pc_db.collection('Users')
    pc_docs = pc_ref.stream()

    for doc in pc_docs:
        try:
            gender = (doc.to_dict())['gender']
        except:
            gender = ""
        try:
            key_pg = (doc.to_dict())['key_pg']
        except:
            key_pg = ""
        try:
            mail = (doc.to_dict())['mail']
        except:
            mail = ""
        try:
            password = (doc.to_dict())['password']
        except:
            password = ""
        try:
            userName = (doc.to_dict())['userName']
        except:
            userName = ""
        try:
            userType = (doc.to_dict())['userType']
        except:
            userType = ""
        try:
            yearOfBirth = (doc.to_dict())['yearOfBirth']
        except:
            yearOfBirth = ""

        Users = {
            'gender': f'{gender}',
            'key_pg': f'{key_pg}',
            'mail': f'{mail}',
            'password': f'{password}',
            'userName': f'{userName}',
            'userType': f'{userType}',
            'yearOfBirth': f'{yearOfBirth}'
        }

        client_ref.document().set(Users)


def update_Votes():
    client_ref = client_db.collection('Votes')

    pc_ref = pc_db.collection('Votes')
    pc_docs = pc_ref.stream()

    for doc in pc_docs:
        try:
            proposition_key = (doc.to_dict())['proposition_key']
        except:
            proposition_key = ""
        try:
            user_choice = (doc.to_dict())['user_choice']
        except:
            user_choice = ""
        try:
            user_id = (doc.to_dict())['user_id']
        except:
            user_id = ""
        try:
            user_key_pg = (doc.to_dict())['user_key_pg']
        except:
            user_key_pg = ""
        try:
            vote_date = (doc.to_dict())['vote_date']
        except:
            vote_date = ""
        try:
            vote_grade = (doc.to_dict())['vote_grade']
        except:
            vote_grade = ""

        Votes = {
            'proposition_key': f'{proposition_key}',
            'user_choice': f'{user_choice}',
            'user_id': f'{user_id}',
            'user_key_pg': f'{user_key_pg}',
            'vote_date': f'{vote_date}',
            'vote_grade': f'{vote_grade}'
        }

        client_ref.document().set(Votes)


if __name__ == '__main__':
    print("update_MembersVotes")
    update_MembersVotes()
    print("update_PoliticalGroups")
    update_PoliticalGroups()
    print("update_Propositions")
    update_Propositions()
    print("update_Users")
    update_Users()
    print("update_Votes")
    update_Votes()
