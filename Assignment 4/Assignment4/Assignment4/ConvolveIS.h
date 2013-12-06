#pragma once
class ConvolveIS
{
public:
	ConvolveIS(void);
	~ConvolveIS(void);

	int main(int numArgs, char* args[]);
	void writeWaveFile(char *fileName, int numSamples, float *signal);
	float* readData(char *fileName, float *signal, int *Thesize);
};

