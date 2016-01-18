% noun generator snippet

#include "../../build/greek/symbols.fst"

$dictionary$ = <u>coretests\.n67485\_0</u><u>lexent\.n67485</u>mhn<noun><fem><is\_ios><stemultacc>\:\:<is\_ios><u>nouninfl\.is\_ios1</u>is<fem><nom><sg>


$=nounclass$ = [#nounclass#]
$noun$ = <u>[#urnchar#]+ [#period#] [#urnchar#]+</u><u>lexent[#period#][#urnchar#]+</u>[#stemchars#]* <noun>$=gender$ $=nounclass$ [#persistacc#] $separator$+ $=nounclass$ <u>[#urnchar#]+[#period#][#urnchar#]+</u> [#stemchars#]* $=gender$ $case$ $number$


#analysissymbol# = #urn# #editorial# #stemtype# #separator# #urntag#
#surfacesymbol# = #character#  #morphtag#


ALPHABET = [#surfacesymbol#] [#analysissymbol#]
$gen$ = <u>:<>[#urnchar#]:<>+ [#period#]:<> [#urnchar#]:<>+</u>:<><u>lexent[#period#][#urnchar#]+</u>[#stemchars#]:<>*<noun>:<>$=gender$$=nounclass$[#persistacc#]:<>[#separator#]:<>+$=nounclass$<u>:<>[#urnchar#]:<>+[#period#]:<>[#urnchar#]:<>+</u>:<>[#stemchars#]:<>* $=gender$ $case$ $number$


#analysissymbol# =  #gender#  #stemtype#
#surfacesymbol# = #character# #case# #number# #urntag# #urn# #editorial#  #urnchar# \.
ALPHABET = [#surfacesymbol#] [#analysissymbol#]:<>
$trimMatches$ = .*

% Test with data:
$dictionary$  || $gen$   || $trimMatches$







%
