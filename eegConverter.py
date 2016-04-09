#!/usr/bin/python
import sys
import re
#regex = re.compile('--- [\s\w]* ---')
regex = re.compile('.*eeg.*')
regex1 = re.compile('.*config.*')
regex2 = re.compile('.*quantization.*')
tmp = ''
with open (sys.argv[1]) as myfile:
    for line in myfile:
        if regex1.search(line):
            tmp += ''
        elif regex2.search(line):
            tmp += ''
        elif regex.search(line):
            tmp += line
print tmp
