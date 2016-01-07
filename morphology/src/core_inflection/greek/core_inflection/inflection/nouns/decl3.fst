%%% Noun declensions: third declension patterns:
%
% is_ios_ending example: mh=nis, mh/nios
%
$is_ios_ending$ = <is\_ios> (\
  <u>nouninfl\.is\_ios1</u>is[<masc><fem>]<nom><sg> |\
  <u>nouninfl\.is\_ios2</u>ios[<masc><fem>]<gen><sg> |\
  <u>nouninfl\.is\_ios3</u>i[<masc><fem>]<dat><sg> |\
  <u>nouninfl\.is\_ios4</u>in[<masc><fem>]<acc><sg> |\
  <u>nouninfl\.is\_ios5</u>i[<masc><fem>]<voc><sg> |\
  <u>nouninfl\.is\_ios6</u>ies[<masc><fem>]<nom><pl> |\
  <u>nouninfl\.is\_ios7</u>iwn[<masc><fem>]<gen><pl> |\
  <u>nouninfl\.is\_ios8</u>isi[<masc><fem>]<dat><pl> |\
  <u>nouninfl\.is\_ios9</u>ies[<masc><fem>]<acc><pl> |\
  <u>nouninfl\.is\_ios10</u>ies[<masc><fem>]<voc><pl> \
  )


$is_ews_ending$ = <is_ews> (\
  is[<masc><fem>]<nom><sg> |\
  ews[<masc><fem>]<gen><sg> |\
  ei[<masc><fem>]<dat><sg> |\
  in[<masc><fem>]<acc><sg> |\
  i[<masc><fem>]<voc><sg> |\
  eis[<masc><fem>]<nom><pl> |\
  ewn[<masc><fem>]<gen><pl> |\
  esi[<masc><fem>]<dat><pl> |\
  eis[<masc><fem>]<acc><pl> \
)

% eus_ews_ending example: basileu/s, basile/ws
%
$eus_ews_ending$ = <eus_ews> (\
  <u>nouninfl\.eus\_ews1</u>eu/s[<masc>][<nom><voc>]<sg> |\
  <u>nouninfl\.eus\_ews2</u>e/ws[<masc>]<gen><sg> |\
  <u>nouninfl\.eus\_ews3</u>ei\=[<masc>]<dat><sg> |\
  <u>nouninfl\.eus\_ews4</u>e/a[<masc>]<acc><sg> |\
  <u>nouninfl\.eus\_ews6</u>eu\=[<masc><fem>]<voc><sg> |\
  <u>nouninfl\.eus\_ews7</u>ei\=s[<masc>]<nom><pl> |\
  <u>nouninfl\.eus\_ews8</u>e/wn[<masc>]<gen><pl> |\
  <u>nouninfl\.eus\_ews9</u>eu\=si[<masc>]<dat><pl> |\
  <u>nouninfl\.eus\_ews9nu</u>eu\=sin[<masc>]<dat><pl> |\
  <u>nouninfl\.eus\_ews10</u>e/as[<masc>]<acc><pl> \
)

$decl3noun_ending$ = $is_ios_ending$ | $is_ews_ending$ | $eus_ews_ending$


$decl3noun_ending$