
import os

class MyInfArith :

    def __init__(self, op) :
         self.jar_path = os.path.join("/home/nayan-modak/Documents/GITHUB/Infinite_precision_arithmetic", "target", "aarithmetic.jar")
         self.main_path = os.path.join("/home/nayan-modak/Documents/GITHUB/Infinite_precision_arithmetic","myinfarith/MyInfArith")
         self.op = op
        

    def compile(self):
        os.system(f'javac -cp .:{self.jar_path} {self.main_path}.java')

    def run(self,operation,x1,x2):
        os.system(f'java -cp .:{self.jar_path} myinfarith.MyInfArith {self.op} {operation} {x1} {x2}')


def run_maven_install():
    os.system("mvn clean install")
    return 1

def main():
    while True :
        #run_maven_install()
        lst = []
        lst = input("Arguments : ").split(" ")
        A = MyInfArith(lst[0])
        A.compile()

        print("\nOutput :")

        A.run(lst[1], lst[2], lst[3])

if __name__ == "__main__":
    main()
