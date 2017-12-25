== RELEASE NOTES ==
v4.5.15.1709 features the following
* (Wiki) Publish 2017-08 Polish wikis and 2017-09 Spanish wikis with full-text search. Also, 2017-08, 2017-07 English Wikipedia, and 2017-07 German Wikipedia.
* (PC) Fix parser issues including "gplx.Err: index is out of bounds" in Spanish Wikipedia and "Module:Location map:353 Malformed coordinates value" in English Wikipedia
* (PC) Fix online import not working on Mac OS X
* (PC) Fix full-text search on http_server
* (PC) Add customizable footer

For more details about this release, please see:
* http://xowa.org/home/wiki/Blog.html
* http://xowa.org/home/wiki/Change_log.html

See the CHANGE LOG below for a complete list of items specific to this release.

== CONTACT ==
XOWA's website is at http://xowa.org . All files are at https://github.com/gnosygnu/xowa/releases/

If you encounter issues, please post to https://github.com/gnosygnu/xowa/issues

== LICENSE ==
XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

See /xowa/bin/any/xowa/LICENSE.txt for more information. On a Windows system  where XOWA is unzipped to C:\xowa,
the full fill path would be C:\xowa\bin\any\xowa\LICENSE.txt

== INSTALL ==
=== FILES ===
The xowa_app_*** archives have all the files necessary for xowa to run, including the main jars, XULRunner, languages, etc..

The paths below are recommendations. Please feel free to choose any other.

Finally, note that "/xowa/" refers to the XOWA root directory. For example, if you're on a Windows system and unzipped XOWA to "C:\xowa", then "/xowa/wiki/en.wikipedia.org" means "C:\xowa\wiki\en.wikipedia.org"

=== REQUIREMENTS ===
* Java 1.7+
: If you do not have Java 1.7 installed, then please install the latest Java from http://www.java.com/en/download/manual.jsp
: On Ubuntu Linux, you can use OpenJDK by running 'sudo apt-get install openjdk-7-jre'.
* Compression/Decompression program ("unzip")
: The XOWA files will be compressed. If your Operating System does not natively support decompressing, please install 7-Zip from http://7-zip.org.
* 512 MB RAM
: XOWA can run on lower memory machines, but 512 MB is needed for importing the larger wikis (EX: en.wikipedia.org; commons.wikimedia.org)

=== OPERATING SYSTEMS ===
===== Windows 64-bit =====
* Download 'xowa_app_windows_64_v*.*.*.*.zip'
* Unzip to 'C:\xowa'. When you are done you should have a file called 'C:\xowa\xowa_64.exe' as well as many others
* Double-click 'xowa_64.exe'
* If "xowa_64.exe" fails, you may have a 32-bit Java installation. Try the "Windows 32-bit" installation below

===== Windows 32-bit =====
[NOTE: This is the same as Windows 64-bit, except the non-"_64" files are used]
* Download 'xowa_app_windows_v*.*.*.*.zip'
* Unzip to 'C:\xowa'. When you are done you should have a file called 'C:\xowa\xowa.exe' as well as many others
* Double-click 'xowa.exe'

==== Linux 64-bit ====
* Download 'xowa_app_linux_64_v*.*.*.*.zip'
* Unzip to '/home/your_user_name/xowa/'. When you are done you should have a file called '/home/your_user_name/xowa/xowa_linux_64.jar' as well as many others
* Open a terminal and run "sh /home/your_user_name/xowa/xowa_linux_64.sh"
* If "xowa_linux_64.sh" doesn't work, run "java -Xmx256m -jar /home/your_user_name/xowa/xowa_linux_64.jar"

==== Linux ====
[NOTE: This is the same as Linux 64-bit, except the non-"_64" files are used]
* Download 'xowa_app_linux_v*.*.*.*.zip'
* Unzip to '/home/your_user_name/xowa/'. When you are done you should have a file called '/home/your_user_name/xowa/xowa_linux.jar' as well as many others
* Open a terminal and run "sh /home/your_user_name/xowa/xowa_linux.sh"
* If "xowa_linux.sh" doesn't work, run "java -Xmx256m -jar /home/your_user_name/xowa/xowa_linux.jar"

==== Mac OS X 64-bit ====
* Download 'xowa_app_macosx_64_v*.*.*.*.zip'
* Unzip to '/Users/your_user_name/xowa/'. When you are done you should have a file called '/Users/your_user_name/xowa/xowa_macosx_64.jar' as well as many others
* Open a terminal by doing Finder -> Applications -> Utilities -> Terminal
* Run "sh /Users/your_user_name/xowa/xowa_macosx_64"
* If "xowa_macosx.sh" doesn't work, run "java -Xmx256m -d32 -XstartOnFirstThread -jar /Users/your_user_name/xowa/xowa_macosx_64.jar"

==== Mac OS X 32-bit ====
[NOTE: This is the same as Mac OS X 64-bit, except the non-"_64" files are used]
* Download 'xowa_app_macosx_v*.*.*.*.zip'
* Unzip to '/Users/your_user_name/xowa/'. When you are done you should have a file called '/Users/your_user_name/xowa/xowa_macosx.jar' as well as many others
* Open a terminal by doing Finder -> Applications -> Utilities -> Terminal
* Run "sh /Users/your_user_name/xowa/xowa_macosx"
* If "xowa_macosx.sh" doesn't work, run "java -Xmx256m -d32 -XstartOnFirstThread -jar /Users/your_user_name/xowa/xowa_macosx.jar"


== USAGE ==
=== Setting up Simple Wikipedia ===
* Once XOWA loads, you will see the Main Page
* Go to the "New users" section and click the link for "Set up Simple Wikipedia". Wait about 3 minutes for the wiki to download and install. When it is finished, it will open Simple Wikipedia
* Browse Simple Wikipedia. When you are done, click on the Main Page link under XOWA in the left hand navigation bar.

Here are some example pages to visit (you can copy and paste these into the address bar):
* http://simple.wikipedia.org/wiki/Gothic_architecture
* http://simple.wikipedia.org/wiki/Saturn_(planet)
* http://simple.wikipedia.org/wiki/Chess
* http://simple.wikipedia.org/wiki/World_History

=== Setting up other wikis ===
* If you want to load English Wikipedia or any other wiki, do any of the following:
** Click on "Import Online" on the XOWA Main Page
** Enter "Help:Import/List" in the address bar near the top of the screen
** Using the Menu, do "Tools" -> "Import online"

=== Downloading offline thumbs ===
* If you want to download a complete set of images for your wiki, see the following links:
** For instructions, see: http://gnosygnu.github.io/xowa/setup_simplewiki.html and http://gnosygnu.github.io/xowa/setup_enwiki.html
** For a list of image databases see http://gnosygnu.github.io/xowa/image_dbs.html or https://archive.org/search.php?query=xowa


== TROUBLESHOOTING ==
=== Windows ===
==== Administrator privileges ====
If you're on Windows Vista, 7, 8, or 10, try running the program with administrator privileges. You can do so by right-clicking on the exe and choosing "Run as administrator"

==== XULRunner ====
Try double-clicking the xulrunner.exe.
* Go to C:\xowa\bin\windows_64\xulrunner_v24 (or on 32-bit machines, C:\xowa\bin\windows\xulrunner_v24)
* Double-click xulrunner.exe
* You should get a message box that starts off with "Mozilla XUL Runner "
* If it fails with "Can't start because MSVCR100.dll is missing...try reinstalling to fix...", then you will need to install the Visual C++ redistributable. See http://sourceforge.net/p/xowa/discussion/general/thread/cc7d867d/?limit=25&page=1#8d38 or http://answers.microsoft.com/en-us/windows/forum/windows_7-windows_programs/trying-to-open-computer-management-the-program/5c9d301a-2191-4edb-916e-5e4958558090?auth=1

==== Run by command-line ====
If the .exe fails, you can try running the jar by the command-line.
* Click on Start and go to Run
* Enter "cmd". A console shows up.
* Enter "java -Xmx256m -jar C:\xowa\xowa_windows_64.jar"

==== Run using a specific JRE ====
* Visit http://www.oracle.com/technetwork/java/javase/downloads/index.html
* Click the "Download" button under "JRE"
**  Choose the Windows x86 version
::  As of the time of this writing, "Windows x86 Offline" links to http://download.oracle.com/otn-pub/java/jdk/7u40-b43/jre-7u40-windows-i586.exe
**  Choose an installation folder. For example, "C:\Program Files (x86)\Java\jre7_x86"
**  After installing, run the following in the command-line: "C:\Program Files (x86)\Java\jre7_x86\bin\java" -Xmx256m -jar C:\xowa\xowa_windows.jar

==== Java installation ====
Check that your Java installation is installed correctly
* Run the following in the command-line: "java -version"
* It should report "java version "1.7****" or higher

==== Arabic characters in path ====
XOWA cannot run in a folder with Arabic characters due to a limitation in one of its sub-components (SWT). For example, C:\موسوعات\XOWA will not work. On the other hand, C:\encyclopedia\XOWA will work. For more info, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=443044

=== Mac OS X ===
==== Missing Java ====
If you get a "No Java runtime present" you will need to set up Java on your machine. There are two options:
* Update to the latest Java 6 by going to http://support.apple.com/kb/DL1572?viewlocale=en_US
: This will be simpler. However, note that Java 6 is an older obsolete version.
* Download Java by going to java.com
: This will be the most up-to-date Java version. However it requires additional steps.

To install Java, do the following:
* Find your Java runtime: Run "/Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java -version" in the terminal to verify the path
* Assuming your java is at the above location, run XOWA by doing either of the following
: (1) Run the following from the terminal:
:     /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java -Xmx256m -XstartOnFirstThread -Xdock:name=XOWA /Users/your_user_name/xowa/xowa_macosx_64.jar
: (2) Change your official java to the plugin version and then run xowa_macosx_64.sh
:     sudo ln -fs /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java /usr/bin/java

=== Linux ===
==== Symbolic links sometimes fails ====
XOWA sometimes fails to import large wikis when symbolic links are in the directory. See: https://github.com/gnosygnu/xowa/issues/16#issuecomment-127458416

==== Downloads fail with "ssl peer shut down incorrectly" ====
On some systems, XOWA fails to download any files with the message "ssl peer shut down incorrectly". The underlying cause is currently unknown, and any insight is appreciated here. It may be related to old Java versions or atypical network setups (VPN?).

The fix was to specify https.protocols=TLSv1 in the startup. For example:
* On Windows: "java -Dhttps.protocols=TLSv1 -Xmx256m -jar xowa_windows_64.jar"
* On Linux: "export SWT_GTK3=0 && java -Dhttps.protocols=TLSv1 -Xmx256m -jar xowa_linux_64.jar"
* On Mac OS X: "java -Dhttps.protocols=TLSv1 -Xmx256m -jar xowa_macosx_64.jar"

=== OpenSUSE and "java.lang.InternalError at sun.security.ec.SunEC.initialize" ===
Recent versions of OpenSuse and Java 1.8 may throw an error like the following:

    Exception in thread "wiki.import" java.lang.InternalError
    at sun.security.ec.SunEC.initialize(Native Method)

To workaround this issue, try the following:

* Open up the following file in text editor:
: /xowa/user/anonymous/app/cfg/os.lnx_64.gfs
* Change line 17 as per the following:
: old: browser_type = 'mozilla'; // 'mozilla' or 'webkit'
: new: browser_type = 'webkit'; // 'mozilla' or 'webkit'

For more info, see https://github.com/gnosygnu/xowa/issues/151

=== Minimal XFCE desktop may fail with org.eclipse.swt.SWTError: No more handles === 
Running on a minimal XFCE desktop may throw an error like the following

    Error: org.eclipse.swt.SWTError: No more handles
    Stack: org.eclipse.swt.SWT.error(Unknown Source)
    org.eclipse.swt.SWT.error(Unknown Source)

To workaround this issue, install the following libraries:
* libjavascriptcoregtk-1_0-0
* libwebkitgtk-1_0-0
* libwebkitgtk2-lang

For more info, see https://github.com/gnosygnu/xowa/issues/151

=== Setting up HTTP Server for Linux ===
* Change xowa_linux_64.sh to something like the following: "java -Dhttp.proxyHost=YOUR_PROXY_HOST -Dhttp.proxyPort=YOUR_PROXY_PORT"
* Install "java-1_8_0-openjdk-headless and libcap-progs"
* (Optional: To listen on port 80) Run "setcap CAP_NET_BIND_SERVICE=+eip /usr/lib64/jvm/jre-1.8.0-openjdk/bin/java"

== CHANGE LOG ==
== v4.5.7.1706 ==
=== Wiki ===
* Package: Publish 2017-08 English Wikipedia.
: Links: [[Wiki_setup/English_wikis]]

* Package: Publish 2017-07 Polish wikis.
: Links: [[Wiki_setup/Polish_wikis]]

* Package: Publish 2017-07 German Wikipedia.
: Links: [[Wiki_setup/German_wikis]]

* Package: Publish 2017-07 English Wikipedia.
: Links: [[Wiki_setup/English_wikis]]

=== PC ===
'''minor'''
* Scribunto: Fix script error '=Module:ISBN:68 attempt to call nil' on many pl.w pages.
: Resolved by: Add addWarning to mw.lua.
: Example: /xowa/bin/any/xowa/xtns/Scribunto/engines/LuaCommon/lualib/mw.lua.
: Links: https://pl.wikipedia.org/wiki/Opus_Dei

* Scribunto: Fix script error 'MWServer.lua:59 vm error: gplx.Err: index is out of bounds' on many es.w pages.
: Resolved by: Upgrade to latest mw.lua.
: Example: /xowa/bin/any/xowa/xtns/Scribunto/engines/LuaCommon/lualib/mw.lua.
: Links: https://es.wikipedia.org/wiki/Ladri_di_biciclette

* Scribunto: Do not call preprocess twice.
: Resolved by: Evaluate template-args when expanding template, not separately afterwards.
: Links: home/wiki/Diagnostics/Scribunto/LuaCommon

* Scribunto: Fix '=Module:Location map:353 Malformed coordinates value' on a few dozen en.w Location pages.
: Resolved by: Return null if integer key is not found.
: Example: if {{coord|3=region:CA-QC|display=inline,title}} then [2] -> null.
: Links: https://en.wikipedia.org/wiki/Sainte-Catherine,_Quebec

* Wikibase: Add "id" to wikibase data table.
: Links: https://es.wikipedia.org/wiki/Premio_Hugo_a_la_mejor_novela

* Luaj: Fix script error '=Module:Authority control:66 vm error: java.lang.ArrayIndexOutOfBoundsException" on some commons pages.
: Message: Script error: =Module:Authority control:66 vm error: java.lang.ArrayIndexOutOfBoundsException: 0
: Resolved by: Check for zero length arrays in string:match.
: Links: http://commons.wikimedia.org/wiki/File:Nouveauxvoyagese-p378.png

* Import: Fix online import not working on Mac OS X. {detected by andyring}
: Links: /xowa/user/anonymous/app/cfg/os.macosx_64.gfs

* Html: Add customizable footer. {requested by Ope30}
: Links: home/wiki/Special:XowaCfg?grp=xowa.html.page

* Html: Add option to hide xowa-alt in html db.
: Links: http://simple.wikipedia.org/wiki/Saturn_(planet)

* Http_server: Fix full-text search not working. {detected by ktry}
: Links: http://localhost:8080/home/wiki/Special:XowaSearch?search=luaj&fulltext=y

* Http_server: Do not override command-line port with config port. {detected by ktry}
: Example: --http_server_port 80.

* Http_server: Add special_pages_safelist. {requested by ktry}
: Example: --http_server.special_pages_safelist Random|AllPages|XowaSearch.

* Http_server: Do not fail if Search has invalid title characters. {detected by ktry}
: Example: Special:XowaSearch?search=mod_date:[20170101 TO 20170131]&fulltext=y.

* DownloadCentral: Increase notification show time from 3 seconds to 30 seconds. {requested by William}

'''trivial'''
* ParserFunctions: Do not fail when calling ifexist with invalid title in Media namespace.
: Links: https://es.wikipedia.org/wiki/Elecciones_presidenciales_de_Venezuela_de_1998

* Hzip: Compress and decompress recent xowa_alt_text.
: Links: https://es.wikipedia.org/wiki/Biome

* Wikibase: Add dinwiki and hiwikiversity.

'''doc'''
* Doc: Provide more instructions on using XOWA as an HTTP server on linux. {detected by ktry}
: Links: readme.txt

