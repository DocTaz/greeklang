% phonology.fst
%
% Definitions of all legal symbols in stem files (lexica) except for:
% 1. stem types (defined in stemtypes.fst)
% 2. URN values (generated by build process from stem files)
%
% Characters for Greek character set:
#consonant# = bgdzqklmncprstfxy
#vowel# = aeiouhw\|
#breathing# = \(\)
#letter# = #consonant# #vowel# #breathing#

#diaeresis# = \+
#accent# = \/\=
#diacritic# = #diaeresis# #accent#

#character# = #letter# #diacritic#
$character$ = [#character#]

% Additional editorial symbols used in stem files:
#morpheme# = <#>
#vowelquant# = <long><short>
#editorial# = #vowelquant# #morpheme#

#separator# = \:
$separator$ = [#separator#]
