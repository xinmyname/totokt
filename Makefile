SRCDIR=./src
PACKAGEDIR=name/xinmy/totokt
OUTDIR=./build
TARGET=$(OUTDIR)/totokt.jar

$(TARGET): $(SRCDIR)/$(PACKAGEDIR)/*.kt
	mkdir -p $(OUTDIR)
	kotlinc $(SRCDIR)/* -include-runtime -d $(TARGET)

clean:
	rm -rf $(OUTDIR)

run:
	java -jar $(TARGET)
