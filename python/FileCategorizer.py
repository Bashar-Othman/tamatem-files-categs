import os
import shutil
import sys

srcDir =sys.argv[1] # for example "E:\\TestJavaProjects\\one-k-files\\files"
destRootDir =sys.argv[2] #for example  "E:\\TestJavaProjects\\one-k-files\\files\\categ"
print("Source : ",srcDir," Destination Root Dir ",destRootDir)
internalDirs=os.listdir(srcDir)
prefixes = set()

def _filePrefixName(fileName):
    if "-" in fileName:
        return fileName.split("-")[0]
    else:
        return None

def createPrefixsList():
    for fname in internalDirs:
        file_path = os.path.join(srcDir, fname)
        if os.path.isfile(file_path):
            prefix = _filePrefixName(fname)
            if len(prefix) > 0:
                prefixes.add(prefix)



def copyFilesBasedOnPrefix():
    for prefixName in prefixes :
        for srcFile in internalDirs:
            srcFilePath = os.path.join(srcDir, srcFile)
            if os.path.isfile(srcFilePath):
                prefix = prefixName  
            if  srcFile.startswith(prefixName) == False :
                continue
                

            if len(prefix) > 0 :
                finalDir = os.path.join(destRootDir, prefix)
                os.makedirs(finalDir, exist_ok=True)
                destFilePath = os.path.join(finalDir, srcFile)
                shutil.copy(srcFilePath, destFilePath)
                print(f"Copying file {srcFilePath} to {destFilePath}")
    print("***************")
    print("Copying files has been completed successfully!!")

    
createPrefixsList()
copyFilesBasedOnPrefix()
