#pragma once
class ReadAndWrite
{
public:
	ReadAndWrite(void);
	~ReadAndWrite(void);


	void writeWaveFile(char *fileName, int numSamples, float *signal);
	float* readData(char *fileName, float *signal, int *readSize);

private:

	char *format;
	int chunkSize;
	char *chunkID;
	char *subChunk1ID;

	short int blockAlign;
	short int bitsPerSample;
	short int audioFormat;
	short int numChannels;
	int sampleRate;
	char *subChunk2ID;
	int dataSize;
	short int* fileData;
	int subChunk1Size;
	int byteRate;

	float minRead;
	float maxRead;
};

