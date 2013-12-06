#include "stdafx.h"
#include "BasicConvolution.h"


BasicConvolution::BasicConvolution(void)
{
	input = new std::ifstream("DrumsDryTest.wav", std::ios::binary);
    impulse = new std::ifstream("l960large_brite_hallTest.wav", std::ios::binary);
	bufferSize = 1000000;
	impulseCounter= 0;
	inputCounter =0;

	tempBuffer = new char[bufferSize];
	inputData = new short int[bufferSize];
	impulseData = new short int[bufferSize];

	readFile(tempBuffer, input, inputDataSize);
	formatData(tempBuffer, inputData, inputDataSize);
	delete(tempBuffer);

	tempBuffer = new char[bufferSize];
	readFile(tempBuffer, impulse, impulseDataSize);
	formatData(tempBuffer, impulseData, impulseDataSize);
	delete(tempBuffer);


	
	//convolute();


	FILE *outputFileStream;
	errno_t error = fopen_s(&outputFileStream,"TestOut.wav", "wb");
	if (outputFileStream == NULL) {
		fprintf(stderr, "File %s cannot be opened for writing\n", "TestOut.wav");
		return;
	}
	writeWaveFileHeader(1, SAMPLE_RATE, SAMPLE_RATE, outputFileStream);

	for (int i = 0; i < (inputDataSize/2) ; i++)
	{
		fwriteShortLSB(inputData[i], outputFileStream);

	}
}


BasicConvolution::~BasicConvolution(void)
{
	//delete(convolutionArray);
	delete(impulseData);
	delete(inputData);
	delete (input);
	delete (impulse);
}

void BasicConvolution:: readFile(char *buffer, std::ifstream *file, size_t &inputDataSize )
{
	std::streampos fileSize;
	file->seekg(0, std::ios::end);
    fileSize = input->tellg();
    file->seekg(0, std::ios::beg);
	inputDataSize = (size_t)fileSize;

	file->read((char*) &buffer[0], fileSize);
}

/*void BasicConvolution::convolute()
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
}*/



void BasicConvolution :: formatData(char *array, short int *newArray, size_t dataSetSize )
{
	//44
	short int formatedValue =0;
	for(size_t i =0; i < dataSetSize; i+=2){
		formatedValue = ((array[i] << 8) & 0xFFFF);
		formatedValue = formatedValue | (array[i+1] & 0xFF);
		formatedValue = endianConverter (formatedValue);
		newArray[i] = formatedValue;
	}
}

short int  BasicConvolution :: endianConverter(short int  val)
{
	    return (val << 8) | ((val >> 8) & 0xFF);
}

/******************************************************************************
*
*       function:       writeWaveFileHeader
*
*       purpose:        Writes the header in WAVE format to the output file.
*
*		arguments:		channels:  the number of sound output channels
*						numberSamples:  the number of sound samples
*                       outputRate:  the sample rate
*						outputFile:  the output file stream to write to
*                       
*       internal
*       functions:      fwriteIntLSB, fwriteShortLSB
*
*       library
*       functions:      ceil, fputs
*
******************************************************************************/

void BasicConvolution :: writeWaveFileHeader(int channels, int numberSamples,
						 double outputRate, FILE *outputFile)
{
	/*  Calculate the total number of bytes for the data chunk  */
	int dataChunkSize = channels * numberSamples * BYTES_PER_SAMPLE;
	
	/*  Calculate the total number of bytes for the form size  */
	int formSize = 36 + dataChunkSize;
	
	/*  Calculate the total number of bytes per frame  */
	short int frameSize = channels * BYTES_PER_SAMPLE;
	
	/*  Calculate the byte rate  */
	int bytesPerSecond = (int)ceil(outputRate * frameSize);

	/*  Write header to file  */
	/*  Form container identifier  */
	fputs("RIFF", outputFile);
      
	/*  Form size  */
	fwriteIntLSB(formSize, outputFile);
      
	/*  Form container type  */
	fputs("WAVE", outputFile);

	/*  Format chunk identifier (Note: space after 't' needed)  */
	fputs("fmt ", outputFile);
      
	/*  Format chunk size (fixed at 16 bytes)  */
	fwriteIntLSB(16, outputFile);

	/*  Compression code:  1 = PCM  */
	fwriteShortLSB(1, outputFile);

	/*  Number of channels  */
	fwriteShortLSB((short)channels, outputFile);

	/*  Output Sample Rate  */
	fwriteIntLSB((int)outputRate, outputFile);

	/*  Bytes per second  */
	fwriteIntLSB(bytesPerSecond, outputFile);

	/*  Block alignment (frame size)  */
	fwriteShortLSB(frameSize, outputFile);

	/*  Bits per sample  */
	fwriteShortLSB(BITS_PER_SAMPLE, outputFile);

	/*  Sound Data chunk identifier  */
	fputs("data", outputFile);

	/*  Chunk size  */
	fwriteIntLSB(dataChunkSize, outputFile);
}



/******************************************************************************
*
*       function:       fwriteIntLSB
*
*       purpose:        Writes a 4-byte integer to the file stream, starting
*                       with the least significant byte (i.e. writes the int
*                       in little-endian form).  This routine will work on both
*                       big-endian and little-endian architectures.
*
*       internal
*       functions:      none
*
*       library
*       functions:      fwrite
*
******************************************************************************/

size_t BasicConvolution :: fwriteIntLSB(int data, FILE *stream)
{
    unsigned char array[4];

    array[3] = (unsigned char)((data >> 24) & 0xFF);
    array[2] = (unsigned char)((data >> 16) & 0xFF);
    array[1] = (unsigned char)((data >> 8) & 0xFF);
    array[0] = (unsigned char)(data & 0xFF);
    return fwrite(array, sizeof(unsigned char), 4, stream);
}



/******************************************************************************
*
*       function:       fwriteShortLSB
*
*       purpose:        Writes a 2-byte integer to the file stream, starting
*                       with the least significant byte (i.e. writes the int
*                       in little-endian form).  This routine will work on both
*                       big-endian and little-endian architectures.
*
*       internal
*       functions:      none
*
*       library
*       functions:      fwrite
*
******************************************************************************/

size_t BasicConvolution :: fwriteShortLSB(short int data, FILE *stream)
{
    unsigned char array[2];

    array[1] = (unsigned char)((data >> 8) & 0xFF);
    array[0] = (unsigned char)(data & 0xFF);
    return fwrite(array, sizeof(unsigned char), 2, stream);
}

