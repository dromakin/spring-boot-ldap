#!/usr/bin/env bash

ldapadd -x -D "cn=admin,dc=example,dc=com" -w admin -H ldap:// -f src/main/resources/ldap-dromakin-com.ldif
