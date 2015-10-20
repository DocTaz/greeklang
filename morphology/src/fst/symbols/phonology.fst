% phonology.fst
%
% Definitions of all legal symbols in stem files (lexica) except for:
% 1. stem types (defined in stemtypes.fst)
% 2. URN values (generated by build process from stem files)
%
% Characters for Greek character set:
#consonant# = bgdzqklmncprstfxy
#vowel# = aeiouhw\|
#breathing# = <sm><ro>
#letter# = #consonant# #vowel# #breathing#

#diaeresis# = \+
#accent# = \/\=
#diacritic# = #diaeresis# #accent#

#character# = #letter# #diacritic#
$character$ = [#character#]

% Additional editorial symbols used in stem files:
#morpheme# = <#>
#vowelquant# = <lo><sh>
#persistacc# = <stemultacc><stempenacc><inflacc>
#editorial# = #vowelquant# #morpheme# #persistacc#


% All valid chars used in stem file:
#stemchars# = #character# #editorial#
#inmorpheme# = #character# #vowelquant# #persistacc#
