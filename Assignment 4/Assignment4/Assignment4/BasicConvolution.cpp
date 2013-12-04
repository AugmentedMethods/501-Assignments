#include "stdafx.h"
#include "BasicConvolution.h"



BasicConvolution::BasicConvolution(void)
{
	bufferSize = 1000000;
	impulseCounter= 0;
	inputCounter =0;

	convolutionArray = new char[bufferSize];
	impulseData = new char[bufferSize];
	inputData  = new char[bufferSize];

	input = std::ifstream("DrumsDryTest.wav", std::ios::binary);
    impulse = std::ifstream("l960large_brite_hallTest.wav", std::ios::binary);

	readInputFile();
	readImpulseFile();

	convolute();
}


BasicConvolution::~BasicConvolution(void)
{
	delete(convolutionArray);
	delete(impulseData);
	delete(inputData);
}

void BasicConvolution:: readInputFile()
{
	input.seekg(0, std::ios::end);
    fileSizeInput = input.tellg();
    input.seekg(0, std::ios::beg);

    input.read((char*) &inputData[0], fileSizeInput);
	//inputCounter +=bufferSize +1;
}

void BasicConvolution:: readImpulseFile()
{
	impulse.seekg(0, std::ios::end);
    fileSizeImpulse = impulse.tellg();
    impulse.seekg(0, std::ios::beg);

    input.read((char*) &impulseData[0], fileSizeImpulse);
	//impulseCounter += bufferSize+1;
}

void BasicConvolution::convolute()
{
	
	int counter = 0;	
	bool check = true;
	std::cout << fileSizeInput <<" : " << fileSizeImpulse << '\n';
	for(size_t i = 40; i < fileSizeInput; i++)
	{

		for(size_t j =40; j<fileSizeImpulse; j++)
		{
			if(convolutionArray[counter+j] == NULL) 
				convolutionArray[counter+j] = (inputData[i] * impulseData[j]);
			else
				convolutionArray[counter+j] += (inputData[i] * impulseData[j]);
		}
		check = true;
		counter++;
	}

}




