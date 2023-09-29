#!/usr/bin/env bash

ldapsearch -x -D "cn=admin,dc=example,dc=com" \
  -w admin -H ldap://localhost:389 \
  -b "ou=users,dc=example,dc=com"