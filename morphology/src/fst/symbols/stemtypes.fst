% stemtypes.fst
%
% Definitions of morphological stem types used to unite stem entries and
% inflectional patterns
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Noun stem types
#noun1# = <a_as><a_as_comp><a_as_long><a_as_short><a_hs><as_ou><as_ou_comp><h_hs><h_hs_comp><hs_ou><hs_ou_comp>


#noun2# = <os_ou><ous_ou><ous_ou_ult>


#labialpalatal3# = <y_pos><y_bos><c_kos><gc_ggos><c_gos><c_xos>

#dental3# = <s_tos><is_idos><is_itos><s_qos><s_ntos><wn_ontos>

#neuttau3# = <ma_matos><r_tos><s_tos>

#liquidnasal3# = <wr_oros><s_nos><wn_onos><hn_enos><null_os>

#rho3# = <hr_ros>

#sigma3# = <hs_ous><as_ws>

#iu3# = <is_ews><us_ews><us_uos>

#diphth3# = <eus_ews><w_ous>


#noun3# = #labialpalatal3#  #dental3# #neuttau3# #liquidnasal3# #rho3# #iu3#  #diphth3#

%%%<gc_ggos><c_gos><c_kos><c_ktos><hs_eos><is_ios><is_ews><eus_ews><ma_matos><s_nos><us_ews><wn_onos><wn_ontos><wr_oros><y_pos>

#irregnoun# = <gunh>

#nounclass# = #noun1# #noun2# #noun3# #irregnoun#

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Adjective stem types
#adj1and2# = <os_h_on>

#adjectiveclass# = #adj1and2#


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Adverb stem types
#indecladv# = <indeclpos><indeclcomp><indeclsup>

#adverbclass# = #indecladv#

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Verb stem types
#omega# = <w_regular> <ew_contract> <aw_contract> <ow_contract> <w_pp1> <w_pp2> <w_pp3> <w_pp4> <w_pp5> <w_pp6>
#mi# = <mi_pres>

#verbclass#  = #omega#


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Indeclinable type
#indeclclass# = <conjunct><particle>


% Union of all stemtypes
#stemtype# = #nounclass# #adjectiveclass# #adverbclass# #verbclass# #indeclclass#
