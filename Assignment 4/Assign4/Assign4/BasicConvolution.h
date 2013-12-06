#pragma once
class BasicConvolution
{
public:
	BasicConvolution(void);
	~BasicConvolution(void);

	void convolve(float x[], int N, float h[], int M, float y[], int P);
	void scale(float signal[], int samples);
	void convolute(char *inputFileName, char *IRFileName, char *outputFileName);
};

