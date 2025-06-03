from scipy.linalg import eigh
import numpy as np
import matplotlib.pyplot as plt

def load_and_center_dataset(filename):
    try:
        x = np.load(filename)
        x = x - np.mean(x, axis=0)
        # print(x)
        return x
    except FileNotFoundError:
        print("The given file was not found.")

def get_covariance(dataset):
    x = np.dot(np.transpose(dataset), dataset)
    S = (1/(len(dataset)-1)) * x
    # print(S.shape)
    return S

def get_eig(S, k):
    eVal, eVec = eigh(S, subset_by_index=[len(S)-k, len(S)-1])
    #copied from https://stackoverflow.com/questions/8092920/sort-eigenvalues-and-associated-eigenvectors-after-using-numpy-linalg-eig-in-pyt
    # tempL = np.sort(eVal)[::-1]
    #sort eigenvalues and eigenvectors from largest to smallest
    idx = eVal.argsort()[::-1] 
    U = eVec[:,idx]
    tempL = eVal[idx]
    #copied format from https://docs.vultr.com/python/third-party/numpy/diag
    #diagonal matrix
    Lambda = np.diag(tempL)
    # print(eVec)
    return Lambda, U

def get_eig_prop(S, prop):
    eVals, eVecs = eigh(S)
    #sort the eigenvalues and eigenvectors from largest to smallest
    idx = eVals.argsort()[::-1] 
    eVecs = eVecs[:,idx]
    eVals = eVals[idx]
    
    # print(eVals)
    # Lambda = np.diag(tempL)
    # print(Lambda)
    #the trace = total of eignvalues 
    trace = np.sum(eVals)
    #eigenvalues and eigenvectors greater than prop proportion of the variance
    pEVals = []
    pEVecs = []
    #copied format from https://www.geeksforgeeks.org/python-iterate-multiple-lists-simultaneously/
    for (i,j) in zip(eVals, eVecs):
        if i/trace > prop:
            pEVals.append(i)
            pEVecs.append(j)
    #copied format from https://stackoverflow.com/questions/26975769/modify-a-particular-row-column-of-a-numpy-array
    #number of columns = number of eigenvalues
    U = eVecs[:, :len(pEVals)]
    Lambda = np.diag(np.asarray(pEVals))
    return Lambda, U

#discussed this with a peer mentor
def project_and_reconstruct_image(image, U):
    #PCA projection
    project = np.dot(np.transpose(U), image)
    #reconstructing the image
    reconstruct = np.dot(U, project)
    return reconstruct
    
def display_image(im_orig_fullres, im_orig, im_reconstructed):
    # Please use the format below to ensure grading consistency
    fig, (ax1, ax2, ax3) = plt.subplots(figsize=(9,3), ncols=3)
    fig.tight_layout()

    reshaped_im_orig_fullres = im_orig_fullres.reshape(218, 178, 3)
    reshaped_im_orig = im_orig.reshape(60,50)
    reshaped_im_reconstructed = im_reconstructed.reshape(60,50)

    #ax1 = orig full res
    #ax2 = orig low res
    #ax3 = reconstruct
    ax1.set_title('Original High Res')
    ax2.set_title('Original')
    ax3.set_title('Reconstructed')

    fullRes = ax1.imshow(reshaped_im_orig_fullres, aspect='equal')
    #discussed with Sadana (another student)
    orig = ax2.imshow(reshaped_im_orig, aspect='equal', cmap = "gray")
    recon = ax3.imshow(reshaped_im_reconstructed, aspect='equal', cmap = "gray")
    fig.colorbar(orig, ax=ax2, location="right")
    fig.colorbar(recon, ax=ax3, location="right")
    
    return fig, ax1, ax2, ax3

def perturb_image(image, U, sigma):
    #random perturbations
    rw = np.random.normal(0, sigma)
    #projection
    project = np.dot(np.transpose(U), image) + rw
    #reconstructing the image
    perturbed = np.dot(U, project)
    return perturbed

def main():
    X = load_and_center_dataset('celeba_60x50.npy')
    # print(len(x))
    # print(len(x[0]))
    S = get_covariance(X)
    # print(len(S))
    # print(len(S[0]))
    Lambda, U = get_eig(S, 50)
    # print(Lambda)
    # print(U)
    # Lambda2, U2 = get_eig_prop(S, 0.07)
    # print(Lambda2)
    # print(U2)
    celeb_idx = 34
    x = X[celeb_idx]
    
    x_fullres = np.load("celeba_218x178x3.npy")[celeb_idx]
    # print(x_fullres.shape)
    # reconstructed = project_and_reconstruct_image(x, U)
    # fig, ax1, ax2, ax3 = display_image(x_fullres, x, reconstructed)
    x_perturbed = perturb_image(x, U, sigma=1000)
    fig, ax1, ax2, ax3 = display_image(x_fullres, x, x_perturbed)
    plt.savefig("output.jpg")

if __name__ == "__main__":
    main()