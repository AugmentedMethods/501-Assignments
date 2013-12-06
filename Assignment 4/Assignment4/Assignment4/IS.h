#pragma once
class IS
{
public:
	IS(void);
	~IS(void);

	void convolve(float x[], int N, float h[], int M, float y[], int P);
	void scale(float signal[], int samples);
	void tbConv(char *inputFileName, char *IRFileName, char *outputFileName);
};

